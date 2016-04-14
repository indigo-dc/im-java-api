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

package es.upv.i3m.grycap.im.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.upv.i3m.grycap.im.client.InfrastructureManagerRestClient;
import es.upv.i3m.grycap.im.client.RestCallParameter;
import es.upv.i3m.grycap.im.client.ServiceResponse;
import es.upv.i3m.grycap.im.exceptions.AuthorizationFileException;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.exceptions.ToscaContentTypeNotSupportedException;
import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

/**
 * This class offers the user an API to communicate with the Infrastructure
 * Manager.
 */
public class InfrastructureManagerApiClient {

  // Path fragments reused in almost all the calls
  private static final String PATH_INFRASTRUCTURES = "infrastructures";
  private static final String PATH_VMS = "vms";
  private static final String PATH_SEPARATOR = "/";
  // IM REST parameters
  private static final String REST_PARAMETER_NAME_CONTEXT = "context";
  private static final String REST_PARAMETER_NAME_VMLIST = "vm_list";
  private static final String REST_PARAMETER_INFRASTRUCTURE_OUTPUTS = "outputs";
  // Rest client needed to make the calls to the IM services
  private InfrastructureManagerRestClient imClient;

  private static final String MEDIA_TYPE_TEXT_WILDCARD = "text/*";

  /**
   * Create a new IM client.
   * 
   * @param targetUrl
   *          : the URL where the REST API of the IM is defined
   * @param authFilePath
   *          : the path where the authorization file is defined
   * @throws ImClientException
   *           : exception in the IM client
   */
  public InfrastructureManagerApiClient(String targetUrl, String authFilePath)
      throws ImClientException {
    setImClient(new InfrastructureManagerRestClient(targetUrl, authFilePath));
  }

  private InfrastructureManagerRestClient getImClient() {
    return imClient;
  }

  private void setImClient(InfrastructureManagerRestClient imClient) {
    this.imClient = imClient;
  }

  /**
   * Return a list of URIs referencing the infrastructures associated to the IM
   * user.
   * 
   * @return : list of URIs
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getInfrastructureList() throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES, MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Return a list of URIs referencing the virtual machines associated to the
   * infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @return : list of URIs
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getInfrastructureInfo(String infId,
      boolean requestJson) throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Return information about the virtual machine with ID vmId associated to the
   * infrastructure with ID infId.<br>
   * The returned string is in RADL format.<br>
   * See more the details of the output in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#getvminfo-xmlrpc">
   * GetVMInfo</a>
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @return : Plain RADL file or Json RADL file.
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getVmInfo(String infId, String vmId,
      boolean requestJson) throws ImClientException {
    return getImClient().get(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + PATH_VMS + PATH_SEPARATOR + vmId,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Return property 'propertyName' from to the virtual machine with ID 'vmId'
   * associated to the infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @param vmProperty
   *          : VM property to retrieve from the virtual machine
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * 
   * @return : a string with the property information
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getVmProperty(String infId, String vmId,
      VmProperties vmProperty, boolean requestJson) throws ImClientException {
    return getImClient().get(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + PATH_VMS + PATH_SEPARATOR + vmId + PATH_SEPARATOR
            + vmProperty.toString(),
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Return the contextualization log associated to the infrastructure with ID
   * 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @return : contextualization log
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getInfrastructureContMsg(String infId,
      boolean requestJson) throws ImClientException {
    return getImClient().get(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + ImValues.CONTMSG,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Return a string with the original RADL specified to create the
   * infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @return : original RADL of the infrastructure
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getInfrastructureRadl(String infId)
      throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + ImValues.RADL, MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Return a JSON with the infrastructure and virtual machines states.<br>
   * The JSON has a 'vm_states' array describing the states of all the machines
   * of the infrastructure and a 'state' that describes the general state of the
   * infrastructure ( e.g. {"vm_states": {"144838424585": "running"}, "state":
   * "running"} ).
   * 
   * @param infId
   *          : infrastructure id
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @return : string with the infrastructure state
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse getInfrastructureState(String infId)
      throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + ImValues.STATE, MediaType.APPLICATION_JSON);
  }

  /**
   * Undeploy the virtual machines associated to the infrastructure with ID
   * 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @return : True if undeploy successful, false otherwise
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse destroyInfrastructure(String infId,
      boolean requestJson) throws ImClientException {
    return getImClient().delete(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD);
  }

  /**
   * Create and configure an infrastructure with the requirements specified in
   * the document of the body contents.<br>
   * If success, it is returned the URI of the new infrastructure.
   * 
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @return : URI of the new infrastructure
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse createInfrastructure(String radlFile,
      RestApiBodyContentType bodyContentType, boolean requestJson)
          throws ImClientException {
    return getImClient().post(PATH_INFRASTRUCTURES,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD,
        radlFile, bodyContentType.getValue());
  }

  /**
   * Add the resources specified in the body contents to the infrastructure with
   * ID 'infId'. The RADL restrictions are the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#addresource-xmlrpc">
   * RPC-XML AddResource</a><br>
   * If success, it is returned a list of URIs of the new virtual machines. <br>
   * The context parameter is optional and is a flag to specify if the
   * contextualization step will be launched just after the VM addition.<br>
   * As default the contextualization flag is set to True.
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @param context
   *          : flag to specify if the contextualization step will be launched
   *          just after the VM addition
   * @return : list of URIs of the new virtual machines
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse addResource(String infId, String radlFile,
      RestApiBodyContentType bodyContentType, boolean requestJson,
      boolean... context) throws ImClientException {

    RestCallParameter parameters = (context != null && context.length > 0)
        ? new RestCallParameter(REST_PARAMETER_NAME_CONTEXT, context[0]) : null;

    return getImClient().post(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD,
        radlFile, bodyContentType.getValue(), parameters);
  }

  /**
   * Undeploy the virtual machine with ID vmId associated to the infrastructure
   * with ID 'infId'.<br>
   * The context parameter is optional and is a flag to specify if the
   * contextualization step will be launched just after the VM addition.<br>
   * As default the contextualization flag is set to True.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * @param context
   *          : flag to specify if the contextualization step will be launched
   *          just after the VM addition
   * @return : True if undeploy successful, false otherwise
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse removeResource(String infId, String vmId,
      boolean requestJson, boolean... context) throws ImClientException {

    RestCallParameter parameters = (context != null && context.length > 0)
        ? new RestCallParameter(REST_PARAMETER_NAME_CONTEXT, context[0]) : null;

    return getImClient().delete(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + PATH_VMS + PATH_SEPARATOR + vmId,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD,
        parameters);
  }

  /**
   * Stop (but do not undeploy) all the virtual machines associated to the
   * infrastructure with ID 'infId'. They can be resumed by the
   * 'startInfrastructure' method.
   * 
   * @param infId
   *          : infrastructure id
   * @return : True if stopping the infrastructure is successful, false
   *         otherwise
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse stopInfrastructure(String infId)
      throws ImClientException {
    return getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + ImValues.STOP,
        MEDIA_TYPE_TEXT_WILDCARD, "", MediaType.TEXT_PLAIN);
  }

  /**
   * Resume all the virtual machines associated to the infrastructure with ID
   * 'infId', previously stopped with the 'stopInfrastructure' method.
   * 
   * @param infId
   *          : infrastructure id
   * @return : True if starting the infrastructure is successful, false
   *         otherwise
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse startInfrastructure(String infId)
      throws ImClientException {
    return getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + ImValues.START,
        MEDIA_TYPE_TEXT_WILDCARD, "", MediaType.TEXT_PLAIN);
  }

  /**
   * Perform the 'stop' action in the virtual machine with ID 'vmId' associated
   * to the infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @return : True if stopping the vm is successful, false otherwise
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse stopVm(String infId, String vmId)
      throws ImClientException {
    return getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + PATH_VMS + PATH_SEPARATOR + vmId + PATH_SEPARATOR + ImValues.STOP,
        MEDIA_TYPE_TEXT_WILDCARD, "", MediaType.TEXT_PLAIN);
  }

  /**
   * Perform the 'start' action in the virtual machine with ID 'vmId' associated
   * to the infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @return : True if starting the vm is successful, false otherwise
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse startVm(String infId, String vmId)
      throws ImClientException {
    return getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + PATH_VMS + PATH_SEPARATOR + vmId + PATH_SEPARATOR
            + ImValues.START,
        MEDIA_TYPE_TEXT_WILDCARD, "", MediaType.TEXT_PLAIN);
  }

  /**
   * Change the features of the virtual machine with ID 'vmId' in the
   * infrastructure with with ID 'infId', specified by the RADL document
   * specified in the body contents.<br>
   * Return a RADL with information about the virtual machine, like
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#getvminfo-xmlrpc">
   * GetVMInfo</a>.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL or RADL_JSON
   * @param requestJson
   *          : specifies the format of the result in the ServiceResponse
   * 
   * @return : RADL with information about the virtual machine modified
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse alterVm(String infId, String vmId, String radlFile,
      RestApiBodyContentType bodyContentType, boolean requestJson)
          throws ImClientException {
    // The content type must not be TOSCA
    failIfToscaContentType(bodyContentType);
    return getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + PATH_VMS + PATH_SEPARATOR + vmId,
        requestJson ? MediaType.APPLICATION_JSON : MEDIA_TYPE_TEXT_WILDCARD,
        radlFile, bodyContentType.getValue());
  }

  /**
   * Perform the reconfigure action in all the virtual machines in the the
   * infrastructure with ID 'infID'.<br>
   * This method starts the contextualization process again.<br>
   * To reconfigure the VMs see the reconfigure methods that include the 'radl'
   * parameter.
   * 
   * @param infId
   *          : infrastructure id
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse reconfigure(String infId) throws ImClientException {
    return getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + ImValues.RECONFIGURE,
        MEDIA_TYPE_TEXT_WILDCARD, "", RestApiBodyContentType.RADL.getValue());
  }

  /**
   * Perform the reconfigure action in all the virtual machines of the
   * infrastructure with ID 'infID.' It updates the configuration of the
   * infrastructure as indicated in the 'radlFile'. The RADL restrictions are
   * the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#reconfigure-xmlrpc">
   * RPC-XML Reconfigure</a><br>
   * If no RADL is specified, the contextualization process is started again.
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the infrastructure properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL or RADL_JSON
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse reconfigure(String infId, String radlFile,
      RestApiBodyContentType bodyContentType) throws ImClientException {
    // The content type must not be TOSCA
    failIfToscaContentType(bodyContentType);
    ServiceResponse response = getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + ImValues.RECONFIGURE,
        MEDIA_TYPE_TEXT_WILDCARD, radlFile, bodyContentType.getValue());
    checkNullValue(response);
    return response;
  }

  /**
   * Perform the reconfigure action in all the virtual machines of the
   * infrastructure with ID 'infID.' It updates the configuration of the
   * infrastructure as indicated in the 'radlFile'. The RADL restrictions are
   * the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#reconfigure-xmlrpc">
   * RPC-XML Reconfigure</a><br>
   * If no RADL is specified, the contextualization process is started again.
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the infrastructure properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL or RADL_JSON
   * @param vmList
   *          : comma separated list of IDs of the VMs to reconfigure. If not
   *          specified all the VMs will be reconfigured.
   * @throws ImClientException
   *           : exception in the IM client
   */
  public ServiceResponse reconfigure(String infId, String radlFile,
      RestApiBodyContentType bodyContentType, List<Integer> vmList)
          throws ImClientException {
    // The content type must not be TOSCA
    failIfToscaContentType(bodyContentType);
    RestCallParameter parameters =
        new RestCallParameter(REST_PARAMETER_NAME_VMLIST, vmList);
    ServiceResponse response = getImClient().put(
        PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
            + ImValues.RECONFIGURE,
        MEDIA_TYPE_TEXT_WILDCARD, radlFile, bodyContentType.getValue(),
        parameters);
    checkNullValue(response);
    return response;
  }

  /**
   * Return a class that includes the outputs defined in the TOSCA document.
   * 
   * @param infId
   *          : infrastructure id
   * @return : InfrastructureStatus class with an internal map containing the
   *         outputs
   * @throws ImClientException
   *           : exception in the IM client
   */
  public InfrastructureStatus getInfrastructureOutputs(String infId)
      throws ImClientException {
    checkNullValue(infId);
    InfrastructureStatus infrastructureStatus = null;
    try {
      ServiceResponse response = getImClient().get(
          PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
              + REST_PARAMETER_INFRASTRUCTURE_OUTPUTS,
          MediaType.APPLICATION_JSON);
      infrastructureStatus = new ObjectMapper().readValue(response.getResult(),
          InfrastructureStatus.class);

    } catch (AuthorizationFileException | IOException exception) {
      ImJavaApiLogger.severe(InfrastructureManagerApiClient.class, exception);
      throw new ImClientException(ImMessages.EXCEPTION_INFRASTRUCTURE_OUTPUTS);
    }
    checkNullValue(infrastructureStatus);
    return infrastructureStatus;
  }

  /**
   * Checks the content type passed. If it's TOSCA throws an exception.
   * 
   * @param bodyContentType
   *          : type of the content sent in the body of the message
   * @throws ImClientException
   *           : exception in the IM client
   */
  private void failIfToscaContentType(RestApiBodyContentType bodyContentType)
      throws ImClientException {
    checkNullValue(bodyContentType);
    if (bodyContentType.equals(RestApiBodyContentType.TOSCA)) {
      ImJavaApiLogger.severe(InfrastructureManagerApiClient.class,
          ImMessages.EXCEPTION_TOSCA_NOT_SUPPORTED);
      throw new ToscaContentTypeNotSupportedException();
    }
  }

  /**
   * Throws null pointer exception if the value passed is null.
   * 
   * @param value
   *          : generic object to check for null
   * @throws NullPointerException
   *           : generic null exception
   */
  private void checkNullValue(Object value) {
    // Fail fast instead of return a null value
    if (value == null) {
      ImJavaApiLogger.severe(this.getClass(), ImMessages.EXCEPTION_NULL_VALUE);
      throw new NullPointerException();
    }
  }
}
