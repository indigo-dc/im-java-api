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

import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * Stores the response of the IM and simplifies some status checking.
 */
public class ServiceResponse {

  private final Response response;
  private final String result;

  /**
   * Creates a new ServiceResponse and checks that the value passed is not null.
   * 
   * @param response
   *          : response of the IM REST service
   */
  public ServiceResponse(Response response) {
    this.response = response;
    this.result = getResponse().readEntity(String.class);
    checkNullValue(this.response);
    checkNullValue(result);
  }

  private Response getResponse() {
    return response;
  }

  public String getResult() {
    return result;
  }

  private void checkNullValue(Object value) {
    // Runtime exception if returns a null value
    if (value == null) {
      ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_NULL_VALUE);
      throw new NullPointerException();
    }
  }

  /**
   * Reason phrase of the service (e.g. 'Not Found', 'Bad request', ...)
   */
  public String getReasonPhrase() {
    return getServiceStatusInfo().getReasonPhrase();
  }

  /**
   * Status code of the service (e.g. 200, 404, ...)
   */
  public int getServiceStatusCode() {
    return getResponse().getStatus();
  }

  /**
   * @see javax.ws.rs.core.Response#getStatusInfo()
   */
  public StatusType getServiceStatusInfo() {
    return getResponse().getStatusInfo();
  }

  /**
   * Return true if the message is successful, i.e. the message has one of the
   * following status codes 2xx. Return false otherwise
   */
  public boolean isReponseSuccessful() {
    return getServiceStatusInfo().getFamily() == Family.SUCCESSFUL;
  }

  /**
   * Reads the returned string by the server.
   */
  @Override
  public String toString() {
    return getResult();
  }

}
