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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.SslConfigurator;

import es.upv.i3m.grycap.file.FileIO;
import es.upv.i3m.grycap.im.exceptions.AuthorizationFileException;
import es.upv.i3m.grycap.im.exceptions.InfrastructureManagerRestClientException;
import es.upv.i3m.grycap.im.lang.ImMessages;
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
    // Identifier of an ssl url
    private static final String SSL_URL = "https";

    /**
     * Creates a new client using the 'targetUrl' as endpoint<br>
     * Loads the authorization file from the parameter 'authFilePath'.
     * 
     * @param targetUrl
     *            : url of the IM rest service
     * @param authFilePath
     *            : path of the authorization file
     * @throws CredentialsTypeNotDefinedException
     * @throws AuthorizationFileException
     */
    public InfrastructureManagerRestClient(String targetUrl, String authFilePath) throws AuthorizationFileException {
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

    private void setAuthFile(String authFilePath) throws AuthorizationFileException {
        try {
            this.authFile = FileIO.readUTF8FileAndReplaceNewLines(authFilePath);
        } catch (IOException e) {
            ImJavaApiLogger.severe(this.getClass(), e);
            throw new AuthorizationFileException(ImMessages.EXCEPTION_AUTHORIZATION_FILE_ERROR);
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
     * @throws InfrastructureManagerRestClientException
     */
    public ServiceResponse get(String path, String requestType, RestCallParameter... parameters)
            throws InfrastructureManagerRestClientException {
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
     * @throws InfrastructureManagerRestClientException
     */
    public ServiceResponse post(String path, String requestType, String content, String postType,
            RestCallParameter... parameters) throws InfrastructureManagerRestClientException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: POST '" + getTarget() + "/" + path + "'");
        // Normalize the content in case of null
        String postContent;
        if (content == null) {
            ImJavaApiLogger.info(InfrastructureManagerRestClient.class, ImMessages.INFO_EMPTY_POST_CONTENT);
            postContent = "";
        } else {
            postContent = content;
        }
        ImJavaApiLogger.debug(this.getClass(), "POST content '" + content + "'");
        return new ServiceResponse(
                getRestClient(path, requestType, parameters).post(Entity.entity(postContent, postType)));
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
     * @throws InfrastructureManagerRestClientException
     */
    public ServiceResponse put(String path, String requestType, String content, String putType,
            RestCallParameter... parameters) throws InfrastructureManagerRestClientException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: PUT '" + getTarget() + "/" + path + "'");

        // Normalize the content in case of null
        String putContent;
        if (content == null) {
            ImJavaApiLogger.info(InfrastructureManagerRestClient.class, ImMessages.INFO_EMPTY_PUT_CONTENT);
            putContent = "";
        } else {
            putContent = content;
        }

        ImJavaApiLogger.debug(this.getClass(), "PUT content '" + putContent + "'");
        return new ServiceResponse(
                getRestClient(path, requestType, parameters).put(Entity.entity(putContent, putType)));
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
     * @throws InfrastructureManagerRestClientException
     */
    public ServiceResponse delete(String path, String requestType, RestCallParameter... parameters)
            throws InfrastructureManagerRestClientException {
        ImJavaApiLogger.info(this.getClass(), "Calling REST service: DELETE '" + getTarget() + "/" + path + "'");
        return new ServiceResponse(getRestClient(path, requestType, parameters).delete());
    }

    /**
     * Returns a new Rest client.
     * 
     * @param path
     *            : url where the client is going to connect
     * @param requestType
     *            : accepted response media types
     * @param parameters
     *            : extra parameters for the call (if needed)
     * @return : new REST client
     * @throws InfrastructureManagerRestClientException
     */
    private Builder getRestClient(String path, String requestType, RestCallParameter... parameters)
            throws InfrastructureManagerRestClientException {
        checkAuthFile();
        checkPath(path);
        boolean ssl = path.startsWith(SSL_URL);
        WebTarget webtarget = buildClient(ssl).target(UriBuilder.fromUri(getTarget()).build()).path(path);
        if (parameters != null && parameters.length > 0 && parameters[0] != null) {
            for (RestCallParameter parameter : parameters) {
                webtarget.queryParam(parameter.getParameterName(), parameter.getParameterValues());
            }
        }
        return buildClient(ssl).target(UriBuilder.fromUri(getTarget()).build()).path(path).request(requestType)
                .header(AUTH_HEADER_TAG, getAuthFile());
    }

    private void checkAuthFile() throws AuthorizationFileException {
        if (getAuthFile() == null || getAuthFile().isEmpty()) {
            ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_AUTHORIZATION_FILE_NOT_FOUND);
            throw new AuthorizationFileException(ImMessages.EXCEPTION_AUTHORIZATION_FILE_NOT_FOUND);
        }
    }

    /**
     * Throws null pointer exception if the value passed is null
     * 
     * @param value
     * @throws InfrastructureManagerRestClientException
     * @throws NullPointerException
     */
    private void checkPath(String path) throws InfrastructureManagerRestClientException {
        if (path == null || path.isEmpty()) {
            ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_PATH_VALUE);
            throw new InfrastructureManagerRestClientException(ImMessages.EXCEPTION_PATH_VALUE);
        }
    }

    /**
     * Build a new Rest client.
     * 
     * @param ssl
     *            : tells the method if the client needs to support SSL or not
     * @return : new REST client
     */
    private static Client buildClient(boolean ssl) {
        return ssl ? ClientBuilder.newBuilder().sslContext(SslConfigurator.newInstance(true).createSSLContext()).build()
                : ClientBuilder.newBuilder().build();
    }

}
