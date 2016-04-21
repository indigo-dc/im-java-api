/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.upv.i3m.grycap.im.rest.client;

import es.upv.i3m.grycap.file.EscapeNewLinesFile;
import es.upv.i3m.grycap.file.FileWithInternalPath;
import es.upv.i3m.grycap.file.NoNullOrEmptyFile;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.exceptions.ImClientErrorException;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.im.pojo.ResponseError;
import es.upv.i3m.grycap.im.rest.client.parameters.RestParameter;
import es.upv.i3m.grycap.im.rest.client.ssl.SslTrustAllClient;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

/**
 * Manage the REST methods to communicate with the IM infrastructure.
 */
public class ImClient {

  private final String targetUrl;
  // Authorization file modified to be sent as value of the Authorization header
  private final String parsedAuthFile;
  private static final String AUTH_HEADER_TAG = "AUTHORIZATION";
  private static final String SSL_URL_ID = "https";
  private final Client client;

  /**
   * Creates a new client using the 'imServiceUrl' as endpoint.<br>
   * Loads the authorization file from the parameter 'authFilePath'.
   * 
   * @param targetUrl
   *          : url of the IM rest service
   * @param authFilePath
   *          : path of the authorization file
   */
  public ImClient(final String targetUrl, final String authFilePath)
      throws ImClientException {
    this.targetUrl = targetUrl;
    this.parsedAuthFile = new FileWithInternalPath(new EscapeNewLinesFile(
        new NoNullOrEmptyFile(new Utf8File(authFilePath)))).read();
    this.client = createRestClient();
  }

  /**
   * Creates a new REST client based on the target URL.
   */
  private Client createRestClient() throws ImClientException {
    RestClient restClient = getTargetUrl().startsWith(SSL_URL_ID)
        ? new SslTrustAllClient() : new NoSslClient();
    return restClient.createClient();
  }

  private String getTargetUrl() {
    return targetUrl;
  }

  private Client getClient() {
    return client;
  }

  private String getParsedAuthFile() {
    return parsedAuthFile;
  }

  /**
   * Generic GET call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param <T>
   *          : class returned by the call
   * @param parameters
   *          : extra parameters for the call (if needed)
   */

  public <T> T get(final String path, final Class<T> type,
      final RestParameter... parameters) throws ImClientException {
    try {
      logCallInfo(HttpMethods.GET, path);
      return configureClient(path, parameters).get(type);

    } catch (WebApplicationException exception) {
      throw new ImClientErrorException(createReponseError(exception));
    }
  }

  /**
   * Generic DELETE call.
   */
  public <T> T delete(final String path, final Class<T> type,
      final RestParameter... parameters) throws ImClientException {
    try {
      logCallInfo(HttpMethods.DELETE, path);
      Builder clientConfigured = configureClient(path, parameters);
      return clientConfigured.delete(type);

    } catch (WebApplicationException exception) {
      throw new ImClientErrorException(createReponseError(exception));
    }
  }

  private static ResponseError
      createReponseError(WebApplicationException exception) {
    Response clientReponse = exception.getResponse();
    String reason = clientReponse.getStatusInfo().getReasonPhrase();
    Integer status = clientReponse.getStatus();
    return new ResponseError(reason, status);
  }

  /**
   * Generic POST call.
   */
  public <T> T post(final String path, final String bodyContent,
      final String contentType, final Class<T> type,
      final RestParameter... parameters) throws ImClientException {
    try {
      // Avoid sending null as body content
      String normalizedBodyContent = normalizeBodyContent(bodyContent);

      logCallInfo(HttpMethods.POST, path);
      logCallContent(HttpMethods.POST, normalizedBodyContent);

      Entity<String> content =
          Entity.entity(normalizedBodyContent, contentType);
      Builder clientConfigured = configureClient(path, parameters);
      return clientConfigured.post(content, type);
    } catch (WebApplicationException exception) {
      throw new ImClientErrorException(createReponseError(exception));
    }
  }

  /**
   * Simplified PUT call.
   */
  public <T> T put(final String path, final Class<T> type,
      final RestParameter... parameters) throws ImClientException {
    return put(path, "", MediaType.TEXT_PLAIN, type, parameters);
  }

  /**
   * Generic PUT call.
   */
  public <T> T put(final String path, final String bodyContent,
      final String contentType, final Class<T> type,
      final RestParameter... parameters) throws ImClientException {
    try {
      // Avoid sending null as body content
      String normalizedBodyContent = normalizeBodyContent(bodyContent);

      logCallInfo(HttpMethods.PUT, path);
      logCallContent(HttpMethods.PUT, normalizedBodyContent);

      Entity<String> content =
          Entity.entity(normalizedBodyContent, contentType);
      Builder clientConfigured = configureClient(path, parameters);
      return clientConfigured.put(content, type);
    } catch (WebApplicationException exception) {
      throw new ImClientErrorException(createReponseError(exception));
    }
  }

  private void logCallInfo(final HttpMethods httpMethodUsed,
      final String pathUsed) {
    ImJavaApiLogger.debug(this.getClass(),
        "Calling REST service: " + httpMethodUsed.getValue() + " '"
            + getTargetUrl() + "/" + pathUsed + "'");
  }

  private void logCallContent(final HttpMethods httpMethodUsed,
      final String bodyContent) {
    ImJavaApiLogger.debug(this.getClass(),
        httpMethodUsed.getValue() + " content '" + bodyContent + "'");
  }

  /**
   * Avoid sending a null content in the body of the REST call.
   * 
   * @param bodyContent
   *          : content of the call body
   * @return : the same body content if not null and an empty string if null
   */
  private String normalizeBodyContent(final String bodyContent) {
    if (bodyContent != null) {
      return bodyContent;
    } else {
      ImJavaApiLogger.info(ImClient.class, ImMessages.INFO_EMPTY_BODY_CONTENT);
    }
    return "";
  }

  /**
   * Returns a Rest client configured with the specified properties.
   * 
   * @param path
   *          : path where the client is going to connect (e.g.
   *          /infrastructures/asdalk-asd34/vms)
   * @param parameters
   *          : extra parameters for the call (if needed)
   * @return : REST client
   */
  public Builder configureClient(final String path,
      final RestParameter... parameters) throws ImClientException {
    try {
      WebTarget webtarget = getClient()
          .target(UriBuilder.fromUri(getTargetUrl()).build()).path(path);

      if (parameters != null && parameters.length > 0
          && parameters[0] != null) {
        for (RestParameter parameter : parameters) {
          webtarget.queryParam(parameter.getName(), parameter.getValues());
        }
      }

      return webtarget.request(MediaType.APPLICATION_JSON)
          .header(AUTH_HEADER_TAG, getParsedAuthFile());
    } catch (IllegalArgumentException | UriBuilderException exception) {
      ImJavaApiLogger.severe(this.getClass(), exception);
      throw exception;
    }
  }
}
