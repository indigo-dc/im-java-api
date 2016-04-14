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
import es.upv.i3m.grycap.im.exceptions.FileException;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.im.rest.client.parameters.RestParameter;
import es.upv.i3m.grycap.im.rest.client.ssl.SslTrustAllClient;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

/**
 * Manage the REST methods to communicate with the IM infrastructure.
 */
public class InfrastructureManagerClient {

  private final String imServiceUrl;
  // Authorization file modified to be sent as value of the Authorization header
  private final String parsedAuthFile;
  private static final String AUTH_HEADER_TAG = "AUTHORIZATION";
  private static final String SSL_URL_ID = "https";

  /**
   * Creates a new client using the 'imServiceUrl' as endpoint.<br>
   * Loads the authorization file from the parameter 'authFilePath'.
   * 
   * @param imServiceUrl
   *          : url of the IM rest service
   * @param authFilePath
   *          : path of the authorization file
   */
  public InfrastructureManagerClient(final String imServiceUrl,
      final String authFilePath) throws FileException {
    this.imServiceUrl = imServiceUrl;
    this.parsedAuthFile = new FileWithInternalPath(new EscapeNewLinesFile(
        new NoNullOrEmptyFile(new Utf8File(authFilePath)))).read();
  }

  private String getImServiceUrl() {
    return imServiceUrl;
  }

  private String getParsedAuthFile() {
    return parsedAuthFile;
  }

  private void logCallInfo(final HttpMethods httpMethodUsed,
      final String pathUsed) {
    ImJavaApiLogger.debug(this.getClass(),
        "Calling REST service: " + httpMethodUsed.getValue() + " '"
            + getImServiceUrl() + "/" + pathUsed + "'");
  }

  private void logCallContent(final HttpMethods httpMethodUsed,
      final String bodyContent) {
    ImJavaApiLogger.debug(this.getClass(),
        httpMethodUsed.getValue() + " content '" + bodyContent + "'");
  }

  /**
   * Avoid sending a null content in the body of the REST call.<br>
   * 
   * @param bodyContent
   *          : content of the call body
   * @return : the same body content if not null and an empty string if null
   */
  private String normalizeBodyContent(final String bodyContent) {
    if (bodyContent != null) {
      return bodyContent;
    } else {
      ImJavaApiLogger.info(InfrastructureManagerClient.class,
          ImMessages.INFO_EMPTY_BODY_CONTENT);
    }
    return "";
  }

  private ServiceResponse genericPostOrPutCall(final HttpMethods httpMethodUsed,
      final String path, final String requestType, final String bodyContent,
      final String contentType, final RestParameter... parameters)
          throws ImClientException {

    // Avoid sending null as body content
    String normalizedBodyContent = normalizeBodyContent(bodyContent);

    logCallInfo(httpMethodUsed, path);
    logCallContent(httpMethodUsed, normalizedBodyContent);

    Builder client = createRestClient(path, requestType, parameters);
    Entity<String> content = Entity.entity(normalizedBodyContent, contentType);
    Response clientResponse = client.put(content);
    return new ServiceResponse(clientResponse);
  }

  /**
   * Generic GET call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param requestType
   *          : accepted response media types
   * @param parameters
   *          : extra parameters for the call (if needed)
   * @return : ServiceResponse wrapper class
   */
  public ServiceResponse get(final String path, final String requestType,
      final RestParameter... parameters) throws ImClientException {
    logCallInfo(HttpMethods.GET, path);
    return new ServiceResponse(
        createRestClient(path, requestType, parameters).get());
  }

  /**
   * Generic POST call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param requestType
   *          : accepted response media types
   * @param bodyContent
   *          : content of the POST call
   * @param contentType
   *          : content type
   * @param parameters
   *          : extra parameters for the call (if needed)
   * @return : ServiceResponse wrapper class
   */
  public ServiceResponse post(final String path, final String requestType,
      final String bodyContent, final String contentType,
      final RestParameter... parameters) throws ImClientException {

    return genericPostOrPutCall(HttpMethods.POST, path, requestType,
        bodyContent, contentType, parameters);
  }

  /**
   * Generic PUT call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param requestType
   *          : accepted response media types
   * @param bodyContent
   *          : content of the PUT call
   * @param contentType
   *          : content type
   * @param parameters
   *          : extra parameters for the call (if needed)
   * 
   * @return : ServiceResponse wrapper class
   * @throws ImClientException
   *           : exception in the rest client
   */
  public ServiceResponse put(final String path, final String requestType,
      final String bodyContent, final String contentType,
      final RestParameter... parameters) throws ImClientException {

    return genericPostOrPutCall(HttpMethods.PUT, path, requestType, bodyContent,
        contentType, parameters);
  }

  /**
   * Generic DELETE call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param requestType
   *          : accepted response media types
   * @param parameters
   *          : extra parameters for the call (if needed)
   * @return : ServiceResponse wrapper class
   * @throws ImClientException
   *           : exception in the rest client
   */
  public ServiceResponse delete(final String path, final String requestType,
      final RestParameter... parameters) throws ImClientException {
    logCallInfo(HttpMethods.DELETE, path);
    Builder client = createRestClient(path, requestType, parameters);
    Response clientResponse = client.delete();
    return new ServiceResponse(clientResponse);
  }

  /**
   * Returns a new Rest client.
   * 
   * @param path
   *          : url where the client is going to connect
   * @param requestType
   *          : accepted response media types
   * @param parameters
   *          : extra parameters for the call (if needed)
   * @return : new REST client
   * @throws InfrastructureManagerRestClientException
   *           : exception in the rest client
   */
  private Builder createRestClient(final String path, final String requestType,
      final RestParameter... parameters) throws ImClientException {
    checkPath(path);
    RestClient client = getImServiceUrl().startsWith(SSL_URL_ID)
        ? new SslTrustAllClient() : new NoSslClient();

    try {
      WebTarget webtarget = client.createClient()
          .target(UriBuilder.fromUri(getImServiceUrl()).build()).path(path);

      if (parameters != null && parameters.length > 0
          && parameters[0] != null) {
        for (RestParameter parameter : parameters) {
          webtarget.queryParam(parameter.getName(), parameter.getValues());
        }
      }
      return webtarget.request(requestType).header(AUTH_HEADER_TAG,
          getParsedAuthFile());
    } catch (IllegalArgumentException | UriBuilderException
        | ImClientException exception) {
      ImJavaApiLogger.severe(this.getClass(), exception);
      throw exception;
    }

  }

  /**
   * Throws null pointer exception if the value passed is null.
   * 
   * @param path
   *          : path to check if null or empty
   * @throws InfrastructureManagerRestClientException
   *           : exception in the rest client
   */
  private void checkPath(final String path) throws ImClientException {
    if (path == null || path.isEmpty()) {
      ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_PATH_VALUE);
      throw new ImClientException(ImMessages.EXCEPTION_PATH_VALUE);
    }
  }

}
