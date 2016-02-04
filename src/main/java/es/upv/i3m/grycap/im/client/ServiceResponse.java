/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.upv.i3m.grycap.im.client;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

/**
 * Response returned by the REST service
 */
public class ServiceResponse {

    private Response response;
    private String result;

    public ServiceResponse(Response response) {
        setResponse(response);
        setServiceResult(getResponse().readEntity(String.class));
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

    private Response getResponse() {
        checkNullValue(response);
        return response;
    }

    /**
     * Gets the returned string by the server
     */
    public String getResult() {
        checkNullValue(response);
        return result;
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

    private void setResponse(Response response) {
        if (response == null) {
            ImJavaApiLogger.warning(this.getClass(), ImMessages.WARNING_NULL_SERVICE_RESPONSE);
        }
        this.response = response;
    }

    private void setServiceResult(String serviceResult) {
        if (serviceResult == null) {
            ImJavaApiLogger.warning(this.getClass(), ImMessages.WARNING_NULL_SERVICE_RESULT);
        }
        this.result = serviceResult;
    }

    /**
     * Reads the returned string by the server
     */
    @Override
    public String toString() {
        return getResult();
    }

}
