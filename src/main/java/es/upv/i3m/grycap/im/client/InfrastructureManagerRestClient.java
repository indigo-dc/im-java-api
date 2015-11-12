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

import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import es.upv.i3m.grycap.file.FileIO;
import es.upv.i3m.grycap.im.exceptions.AuthFileNotFoundException;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

/**
 * Manage the REST methods to communicate with the IM infrastructure
 */
public class InfrastructureManagerRestClient {

    // URL of the REST Service
    private String target;
    // PATH of the Authorization file
    private String authFile;
    // Header tag used to pass the authorization information
    private static final String AUTH_HEADER_TAG = "AUTHORIZATION";

    /**
     * Creates a new client using the 'targetUrl' as endpoint<br>
     * Loads the authorization file from the parameter 'authFilePath'.
     * 
     * @param targetUrl
     *            : url of the IM rest service
     * @param authFilePath
     *            : path of the authorization file
     * @throws CredentialsTypeNotDefinedException
     * @throws AuthFileNotFoundException
     */
    public InfrastructureManagerRestClient(String targetUrl, String authFilePath) throws AuthFileNotFoundException {
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

    private void setAuthFile(String authFilePath) throws AuthFileNotFoundException {
        try {
            this.authFile = FileIO.readUTF8FileAndReplaceNewLines(authFilePath);
        } catch (IOException e) {
            ImJavaApiLogger.severe(this.getClass(), e);
            throw new AuthFileNotFoundException("Error reading the authorization file: " + e.getMessage());
        }
    }

    /**
     * Generic GET call
     * 
     * @param path
     *            : specific path for the call (e.g. infrastructures/02154-659a)
     * @param requestType
     *            : accepted response media types
     * @param parameters
     *            : extra parameters for the call (if needed)
     * @return : ServiceResponse wrapper class
     * @throws AuthFileNotFoundException
     */
    public ServiceResponse get(String path, String requestType, RestCallParameter... parameters)
            throws AuthFileNotFoundException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: GET '" + getTarget() + "/" + path + "'");
        return new ServiceResponse(getRestClient(path, requestType, parameters).get());
    }

    /**
     * Generic POST call
     * 
     * @param path
     *            : specific path for the call (e.g. infrastructures/02154-659a)
     * @param requestType
     *            : accepted response media types
     * @param content
     *            : content of the POST call
     * @param postType
     *            : content type
     * @param parameters
     *            : extra parameters for the call (if needed)
     * @return : ServiceResponse wrapper class
     * @throws AuthFileNotFoundException
     */
    public ServiceResponse post(String path, String requestType, String content, String postType,
            RestCallParameter... parameters) throws AuthFileNotFoundException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: POST '" + getTarget() + "/" + path + "'");
        ImJavaApiLogger.debug(this.getClass(), "POST content '" + content + "'");
        return new ServiceResponse(getRestClient(path, requestType, parameters).post(Entity.entity(content, postType)));
    }

    /**
     * Generic PUT call
     * 
     * @param path
     *            : specific path for the call (e.g. infrastructures/02154-659a)
     * @param requestType
     *            : accepted response media types
     * @param content
     *            : content of the PUT call
     * @param putType
     *            : content type
     * @param parameters
     *            : extra parameters for the call (if needed)
     * 
     * @return : ServiceResponse wrapper class
     * @throws AuthFileNotFoundException
     */
    public ServiceResponse put(String path, String requestType, String content, String putType,
            RestCallParameter... parameters) throws AuthFileNotFoundException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: PUT '" + getTarget() + "/" + path + "'");
        ImJavaApiLogger.debug(this.getClass(), "PUT content '" + content + "'");
        return new ServiceResponse(getRestClient(path, requestType, parameters).put(Entity.entity(content, putType)));
    }

    /**
     * Generic DELETE call
     * 
     * @param path
     *            : specific path for the call (e.g. infrastructures/02154-659a)
     * @param requestType
     *            : accepted response media types
     * @param parameters
     *            : extra parameters for the call (if needed)
     * @return : ServiceResponse wrapper class
     * @throws AuthFileNotFoundException
     */
    public ServiceResponse delete(String path, String requestType, RestCallParameter... parameters)
            throws AuthFileNotFoundException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: DELETE '" + getTarget() + "/" + path + "'");
        return new ServiceResponse(getRestClient(path, requestType, parameters).delete());
    }

    /**
     * Build a new Rest client.
     * 
     * @return : new REST client
     * @throws AuthFileNotFoundException
     */
    private Builder getRestClient(String path, String requestType, RestCallParameter... parameters)
            throws AuthFileNotFoundException {
        if (getAuthFile() != null) {
            if (parameters != null && parameters.length > 0) {
                WebTarget webtarget = ClientBuilder.newBuilder().build().target(UriBuilder.fromUri(getTarget()).build())
                        .path(path);
                for (RestCallParameter parameter : parameters) {
                    webtarget.queryParam(parameter.getParameterName(), parameter.getParameterValues());
                }
                return webtarget.request(requestType).header(AUTH_HEADER_TAG, getAuthFile());
            } else {
                return ClientBuilder.newBuilder().build().target(UriBuilder.fromUri(getTarget()).build()).path(path)
                        .request(requestType).header(AUTH_HEADER_TAG, getAuthFile());
            }
        } else {
            throw new AuthFileNotFoundException("Authorization file not specified");
        }
    }

}
