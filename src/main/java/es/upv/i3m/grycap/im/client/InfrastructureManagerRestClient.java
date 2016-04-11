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

package es.upv.i3m.grycap.im.client;

import es.upv.i3m.grycap.file.EscapedNewLinesFile;
import es.upv.i3m.grycap.file.NoNullOrEmptyFile;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.exceptions.FileException;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

/**
 * Manage the REST methods to communicate with the IM infrastructure.
 */
public class InfrastructureManagerRestClient {

  // URL of the REST Service
  private String target;
  // Authorization file
  private String authFile;
  // Header tag used to pass the authorization information
  private static final String AUTH_HEADER_TAG = "AUTHORIZATION";
  // Identifier of an ssl url
  private static final String SSL_URL = "https";

  /**
   * Creates a new client using the 'targetUrl' as endpoint<br>
   * Loads the authorization file from the parameter 'authFilePath'.
   * 
   * @param targetUrl
   *          : url of the IM rest service
   * @param authFilePath
   *          : path of the authorization file
   * @throws FileException
   *           : file related exception
   */
  public InfrastructureManagerRestClient(String targetUrl, String authFilePath)
      throws FileException {
    setTarget(targetUrl);
    setAuthFile(authFilePath);
  }

  private String getTarget() {
    return target;
  }

  private void setTarget(String target) {
    this.target = target;
  }

  private String getAuthFile() {
    return authFile;
  }

  private void setAuthFile(String authFilePath) throws FileException {
    this.authFile = new EscapedNewLinesFile(
        new NoNullOrEmptyFile(new Utf8File(authFilePath))).read();
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
   * @throws ImClientException
   *           : exception in the rest client
   */
  public ServiceResponse get(String path, String requestType,
      RestCallParameter... parameters) throws ImClientException {
    ImJavaApiLogger.info(this.getClass(),
        "Calling REST service: GET '" + getTarget() + "/" + path + "'");
    return new ServiceResponse(
        getRestClient(path, requestType, parameters).get());
  }

  /**
   * Generic POST call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param requestType
   *          : accepted response media types
   * @param content
   *          : content of the POST call
   * @param postType
   *          : content type
   * @param parameters
   *          : extra parameters for the call (if needed)
   * @return : ServiceResponse wrapper class
   * @throws ImClientException
   *           : exception in the rest client
   */
  public ServiceResponse post(String path, String requestType, String content,
      String postType, RestCallParameter... parameters)
          throws ImClientException {
    ImJavaApiLogger.info(this.getClass(),
        "Calling REST service: POST '" + getTarget() + "/" + path + "'");
    // Normalize the content in case of null
    String postContent;
    if (content == null) {
      ImJavaApiLogger.info(InfrastructureManagerRestClient.class,
          ImMessages.INFO_EMPTY_POST_CONTENT);
      postContent = "";
    } else {
      postContent = content;
    }
    ImJavaApiLogger.debug(this.getClass(), "POST content '" + content + "'");
    return new ServiceResponse(getRestClient(path, requestType, parameters)
        .post(Entity.entity(postContent, postType)));
  }

  /**
   * Generic PUT call
   * 
   * @param path
   *          : specific path for the call (e.g. infrastructures/02154-659a)
   * @param requestType
   *          : accepted response media types
   * @param content
   *          : content of the PUT call
   * @param putType
   *          : content type
   * @param parameters
   *          : extra parameters for the call (if needed)
   * 
   * @return : ServiceResponse wrapper class
   * @throws ImClientException
   *           : exception in the rest client
   */
  public ServiceResponse put(String path, String requestType, String content,
      String putType, RestCallParameter... parameters)
          throws ImClientException {
    ImJavaApiLogger.info(this.getClass(),
        "Calling REST service: PUT '" + getTarget() + "/" + path + "'");

    // Normalize the content in case of null
    String putContent;
    if (content == null) {
      ImJavaApiLogger.info(InfrastructureManagerRestClient.class,
          ImMessages.INFO_EMPTY_PUT_CONTENT);
      putContent = "";
    } else {
      putContent = content;
    }

    ImJavaApiLogger.debug(this.getClass(), "PUT content '" + putContent + "'");
    return new ServiceResponse(getRestClient(path, requestType, parameters)
        .put(Entity.entity(putContent, putType)));
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
  public ServiceResponse delete(String path, String requestType,
      RestCallParameter... parameters) throws ImClientException {
    ImJavaApiLogger.info(this.getClass(),
        "Calling REST service: DELETE '" + getTarget() + "/" + path + "'");
    return new ServiceResponse(
        getRestClient(path, requestType, parameters).delete());
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
  private Builder getRestClient(String path, String requestType,
      RestCallParameter... parameters) throws ImClientException {
    checkPath(path);
    RestClient client = getTarget().startsWith(SSL_URL)
        ? new SslTrustAllRestClient() : new NoSslRestClient();

    try {
      WebTarget webtarget = client.createClient()
          .target(UriBuilder.fromUri(getTarget()).build()).path(path);

      if (parameters != null && parameters.length > 0
          && parameters[0] != null) {
        for (RestCallParameter parameter : parameters) {
          webtarget.queryParam(parameter.getParameterName(),
              parameter.getParameterValues());
        }
      }
      return webtarget.request(requestType).header(AUTH_HEADER_TAG,
          getAuthFile());
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
  private void checkPath(String path) throws ImClientException {
    if (path == null || path.isEmpty()) {
      ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_PATH_VALUE);
      throw new ImClientException(ImMessages.EXCEPTION_PATH_VALUE);
    }
  }

}
