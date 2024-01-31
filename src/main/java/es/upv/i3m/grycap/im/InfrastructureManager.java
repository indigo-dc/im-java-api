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

package es.upv.i3m.grycap.im;

import es.upv.i3m.grycap.im.auth.credentials.AuthorizationHeader;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.exceptions.ToscaContentTypeNotSupportedException;
import es.upv.i3m.grycap.im.lang.ImMessages;
import es.upv.i3m.grycap.im.pojo.InfOutputValues;
import es.upv.i3m.grycap.im.pojo.InfrastructureState;
import es.upv.i3m.grycap.im.pojo.InfrastructureUri;
import es.upv.i3m.grycap.im.pojo.InfrastructureUris;
import es.upv.i3m.grycap.im.pojo.Property;
import es.upv.i3m.grycap.im.pojo.VirtualMachineInfo;
import es.upv.i3m.grycap.im.rest.client.BodyContentType;
import es.upv.i3m.grycap.im.rest.client.ImClient;
import es.upv.i3m.grycap.im.rest.client.parameters.NoParameter;
import es.upv.i3m.grycap.im.rest.client.parameters.Parameter;
import es.upv.i3m.grycap.im.rest.client.parameters.RestParameter;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.List;

/**
 * This class offers the user an API to communicate with the Infrastructure
 * Manager.
 */
public class InfrastructureManager {

  // Path fragments reused in almost all the calls
  private static final String PATH_INFRASTRUCTURES = "infrastructures";
  private static final String PATH_VMS = "vms";
  private static final String PATH_SEPARATOR = "/";
  // IM REST parameters
  private static final String REST_PARAMETER_NAME_CONTEXT = "context";
  private static final String REST_PARAMETER_NAME_ASYNC = "async";
  private static final String REST_PARAMETER_NAME_VMLIST = "vm_list";
  private static final String REST_PARAMETER_NAME_FORCE = "force";

  private final ImClient imClient;

  /**
   * Create a new IM client.
   * 
   * @param targetUrl
   *          : the URL where the REST API of the IM is defined
   * @param authFile
   *          : the authorization file
   */
  public InfrastructureManager(final String targetUrl, final Path authFile)
      throws ImClientException {
    imClient = new ImClient(targetUrl, authFile);
  }

  /**
   * Create a new IM client.
   * 
   * @param targetUrl
   *          : the URL where the REST API of the IM is defined
   * @param authorizationHeader
   *          : string with the authorization content
   */
  public InfrastructureManager(final String targetUrl,
      final String authorizationHeader) throws ImClientException {
    imClient = new ImClient(targetUrl, authorizationHeader);
  }

  /**
   * Create a new IM client.
   * 
   * @param targetUrl
   *          : the URL where the REST API of the IM is defined
   * @param authorizationHeader
   *          : {@link AuthorizationHeader} with the authorization content
   */
  public InfrastructureManager(final String targetUrl,
      final AuthorizationHeader authorizationHeader) throws ImClientException {
    String serializedAuthorizationHeader = authorizationHeader.serialize();
    imClient = new ImClient(targetUrl, serializedAuthorizationHeader);
  }

  private ImClient getImClient() {
    return imClient;
  }

  /**
   * Set the client connection timeout in milliseconds.
   *
   * @param connectTimeout
   *          : int with the client connection timeout
   */
  public void setConnectTimeout(final int connectTimeout) {
    imClient.setConnectTimeout(connectTimeout);
  }

  /**
   * Set the client read timeout in milliseconds.
   *
   * @param readTimeout
   *          : int with the client read timeout
   */
  public void setReadTimeout(final int readTimeout) {
    imClient.setReadTimeout(readTimeout);
  }

  /**
   * Create and configure an infrastructure with the requirements specified in
   * the document of the body contents.<br>
   * If success, it is returned the URI of the new infrastructure.
   * 
   * @param infrastructureDefinition
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @return : URI of the new infrastructure
   */
  public InfrastructureUri createInfrastructure(String infrastructureDefinition,
      BodyContentType bodyContentType) throws ImClientException {

    return getImClient().post(PATH_INFRASTRUCTURES, infrastructureDefinition,
        bodyContentType.getValue(), InfrastructureUri.class);
  }
  
  /**
   * Create and configure an infrastructure with the requirements specified in
   * the document of the body contents.<br>
   * If success, it is returned the URI of the new infrastructure.
   * This call will not wait for the VMs to be created to return.
   * 
   * @param infrastructureDefinition
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @return : URI of the new infrastructure
   */
  public InfrastructureUri createInfrastructureAsync(String infrastructureDefinition,
      BodyContentType bodyContentType) throws ImClientException {

    RestParameter asyncParameter = createCallParameters(REST_PARAMETER_NAME_ASYNC, true);

    return getImClient().post(PATH_INFRASTRUCTURES, infrastructureDefinition,
        bodyContentType.getValue(), InfrastructureUri.class, asyncParameter);
  }  

  /**
   * Return a list of URIs referencing the infrastructures associated to the IM
   * user.
   */
  public InfrastructureUris getInfrastructureList() throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES, InfrastructureUris.class);
  }

  /**
   * Return a list of URIs referencing the virtual machines associated to the
   * infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   */
  public InfrastructureUris getInfrastructureInfo(String infId)
      throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        InfrastructureUris.class);
  }

  /**
   * Return information about the virtual machine with ID vmId associated to the
   * infrastructure with ID infId.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   * @return : POJO with the vm info.
   */
  public VirtualMachineInfo getVmInfo(String infId, String vmId)
      throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + PATH_VMS + PATH_SEPARATOR + vmId,
        VirtualMachineInfo.class);
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
   */
  public Property getVmProperty(String infId, String vmId,
      VmProperties vmProperty) throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + PATH_VMS + PATH_SEPARATOR + vmId + PATH_SEPARATOR
        + vmProperty.toString(), Property.class);
  }

  /**
   * Return the contextualization log associated to the infrastructure with ID
   * 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   */
  public Property getInfrastructureContMsg(String infId)
      throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + ImValues.CONTMSG, Property.class);
  }

  /**
   * Return a json with the original RADL specified to create the infrastructure
   * with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   */
  public Property getInfrastructureRadl(String infId) throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + ImValues.RADL, Property.class);
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
   * @return : json with the infrastructure state
   */
  public InfrastructureState getInfrastructureState(String infId)
      throws ImClientException {
    return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId
        + PATH_SEPARATOR + ImValues.STATE, InfrastructureState.class);
  }

  /**
   * Undeploy the virtual machines associated to the infrastructure with ID
   * 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   */
  public void destroyInfrastructure(String infId) throws ImClientException {
    getImClient().delete(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        String.class);
  }

  /**
   * Undeploy the virtual machines associated to the infrastructure with ID
   * 'infId'.
   * This call will not wait for the VMs to be created to return.
   * 
   * @param infId
   *          : infrastructure id
   * @param force
   *          : flag to specify that the infra will be from the IM although not
              all resources are deleted
   */
  public void destroyInfrastructureAsync(String infId, boolean force) throws ImClientException {
    RestParameter asyncParameter = createCallParameters(REST_PARAMETER_NAME_ASYNC, true);
    RestParameter forceParameter = createCallParameters(REST_PARAMETER_NAME_FORCE, force);
    getImClient().delete(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        String.class, asyncParameter, forceParameter);
  }

  /**
   * Add the resources specified in the body contents to the infrastructure with
   * ID 'infId'. The RADL restrictions are the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#addresource-xmlrpc">
   * RPC-XML AddResource</a><br>
   * If success, it is returned a list of URIs of the new virtual machines. <br>
   * The context parameter is optional and is a flag to specify if the
   * contextualization step will be launched just after the VM addition.<br>
   * If not specified the contextualization flag is set to True.
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @param context
   *          : flag to specify if the contextualization step will be launched
   *          just after the VM addition
   * @return : list of URIs of the new virtual machines
   */
  @Deprecated
  public InfrastructureUris addResource(String infId, String radlFile,
      BodyContentType bodyContentType, boolean... context)
      throws ImClientException {

    RestParameter restParameter = createCallParameters(context);
    return getImClient().post(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        radlFile, bodyContentType.getValue(), InfrastructureUris.class,
        restParameter);
  }

  /**
   * Add the resources specified in the body contents to the infrastructure with
   * ID 'infId'. The RADL restrictions are the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#addresource-xmlrpc">
   * RPC-XML AddResource</a><br>
   * If success, it is returned a list of URIs of the new virtual machines. <br>
   * The context parameter is optional and is a flag to specify if the
   * contextualization step will be launched just after the VM addition.<br>
   * If not specified the contextualization flag is set to True.
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @return : list of URIs of the new virtual machines
   */
  public InfrastructureUris addResource(String infId, String radlFile,
      BodyContentType bodyContentType) throws ImClientException {
    return addResourcesWithParameters(infId, radlFile, bodyContentType);
  }

  /**
   * Add the resources specified in the body contents to the infrastructure with
   * ID 'infId'. The RADL restrictions are the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#addresource-xmlrpc">
   * RPC-XML AddResource</a><br>
   * If success, it is returned a list of URIs of the new virtual machines. <br>
   * The context parameter is optional and is a flag to specify if the
   * contextualization step will be launched just after the VM addition.<br>
   * If not specified the contextualization flag is set to True.
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @param context
   *          : flag to specify if the contextualization step will be launched
   *          just after the VM addition
   * @return : list of URIs of the new virtual machines
   */
  public InfrastructureUris addResource(String infId, String radlFile,
      BodyContentType bodyContentType, boolean context)
      throws ImClientException {

    RestParameter ctxParameter =
        createCallParameters(REST_PARAMETER_NAME_CONTEXT, context);
    
    return getImClient().post(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
          radlFile, bodyContentType.getValue(), InfrastructureUris.class,
          ctxParameter);    
  }

  /**
   * Add the resources specified in the body contents to the infrastructure with
   * ID 'infId'. The RADL restrictions are the same as in
   * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#addresource-xmlrpc">
   * RPC-XML AddResource</a><br>
   * If success, it is returned a list of URIs of the new virtual machines. <br>
   * This call activates the contextualization step just after the VM addition.
   * <br>
   * 
   * @param infId
   *          : infrastructure id
   * @param radlFile
   *          : file with the virtual machine properties and configuration
   * @param bodyContentType
   *          : set the body content type. Can be RADL, RADL_JSON or TOSCA.
   * @return : list of URIs of the new virtual machines
   */
  public InfrastructureUris addResourceAndContextualize(String infId,
      String radlFile, BodyContentType bodyContentType)
      throws ImClientException {

    RestParameter ctxParameter =
        createCallParameters(REST_PARAMETER_NAME_CONTEXT, true);
    return addResourcesWithParameters(infId, radlFile, bodyContentType,
        ctxParameter);
  }

  private InfrastructureUris addResourcesWithParameters(String infId,
      String radlFile, BodyContentType bodyContentType,
      RestParameter... parameters) throws ImClientException {

    return getImClient().post(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId,
        radlFile, bodyContentType.getValue(), InfrastructureUris.class,
        parameters);
  }

  private static RestParameter createCallParameters(String paramName,
      Object value) {
    return new Parameter(paramName, value);
  }

  @Deprecated
  private static RestParameter createCallParameters(boolean... context) {
    return (context != null && context.length > 0)
        ? new Parameter(REST_PARAMETER_NAME_CONTEXT, context[0])
        : new NoParameter();
  }

  /**
   * Undeploy the virtual machine with ID 'vmId' associated to the
   * infrastructure with ID 'infId'. <br>
   * The context parameter is optional and is a flag to specify if the
   * contextualization step will be launched just after the VM addition.<br>
   * As default the contextualization flag is set to True.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmIds
   *          : list of virtual machine ids
   * @param context
   *          : flag to specify if the contextualization step will be launched
   *          just after the VM addition
   * @throws ImClientException
   *           : exception in the IM client
   */
  public void removeResource(String infId, List<String> vmIds,
      boolean... context) throws ImClientException {
    String ids = StringUtils.join(vmIds, ",");
    removeResource(infId, ids, context);
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
   * @param context
   *          : flag to specify if the contextualization step will be launched
   *          just after the VM addition
   */
  public void removeResource(String infId, String vmId, boolean... context)
      throws ImClientException {

    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + PATH_VMS + PATH_SEPARATOR + vmId;
    RestParameter restParameter = createCallParameters(context);

    getImClient().delete(path, String.class, restParameter);
  }

  /**
   * Stop (but do not undeploy) all the virtual machines associated to the
   * infrastructure with ID 'infId'. They can be resumed by the
   * 'startInfrastructure' method.
   * 
   * @param infId
   *          : infrastructure id
   */
  public void stopInfrastructure(String infId) throws ImClientException {
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + ImValues.STOP;
    getImClient().put(path, String.class);
  }

  /**
   * Resume all the virtual machines associated to the infrastructure with ID
   * 'infId', previously stopped with the 'stopInfrastructure' method.
   * 
   * @param infId
   *          : infrastructure id
   */
  public void startInfrastructure(String infId) throws ImClientException {
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + ImValues.START;
    getImClient().put(path, String.class);
  }

  /**
   * Perform the 'stop' action in the virtual machine with ID 'vmId' associated
   * to the infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   */
  public void stopVm(String infId, String vmId) throws ImClientException {

    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + PATH_VMS + PATH_SEPARATOR + vmId + PATH_SEPARATOR + ImValues.STOP;
    getImClient().put(path, String.class);
  }

  /**
   * Perform the 'start' action in the virtual machine with ID 'vmId' associated
   * to the infrastructure with ID 'infId'.
   * 
   * @param infId
   *          : infrastructure id
   * @param vmId
   *          : virtual machine id
   */
  public void startVm(String infId, String vmId) throws ImClientException {
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + PATH_VMS + PATH_SEPARATOR + vmId + PATH_SEPARATOR + ImValues.START;
    getImClient().put(path, String.class);
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
   * 
   * @return : POJO with information about the virtual machine modified
   */
  public VirtualMachineInfo alterVm(String infId, String vmId, String radlFile,
      BodyContentType bodyContentType) throws ImClientException {
    // The content type must not be TOSCA
    failIfToscaContentType(bodyContentType);
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + PATH_VMS + PATH_SEPARATOR + vmId;
    return getImClient().put(path, radlFile, bodyContentType.getValue(),
        VirtualMachineInfo.class);
  }

  /**
   * Checks the content type passed. If it's TOSCA throws an exception.
   * 
   * @param bodyContentType
   *          : type of the content sent in the body of the message
   */
  private static void failIfToscaContentType(BodyContentType bodyContentType)
      throws ImClientException {
    if (bodyContentType.equals(BodyContentType.TOSCA)) {
      ImJavaApiLogger.severe(InfrastructureManager.class,
          ImMessages.EXCEPTION_TOSCA_NOT_SUPPORTED);
      throw new ToscaContentTypeNotSupportedException();
    }
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
   */
  public void reconfigure(String infId) throws ImClientException {
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + ImValues.RECONFIGURE;
    getImClient().put(path, String.class);
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
   */
  public void reconfigure(String infId, String radlFile,
      BodyContentType bodyContentType) throws ImClientException {
    // The content type must not be TOSCA
    failIfToscaContentType(bodyContentType);
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + ImValues.RECONFIGURE;
    getImClient().put(path, radlFile, bodyContentType.getValue(), String.class);
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
   */
  public void reconfigure(String infId, String radlFile,
      BodyContentType bodyContentType, List<Integer> vmList)
      throws ImClientException {
    // The content type must not be TOSCA
    failIfToscaContentType(bodyContentType);
    RestParameter parameters =
        new Parameter(REST_PARAMETER_NAME_VMLIST, vmList);
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + ImValues.RECONFIGURE;
    getImClient().put(path, radlFile, bodyContentType.getValue(), String.class,
        parameters);
  }

  /**
   * Return a class that includes the outputs defined in the TOSCA document.
   * 
   * @param infId
   *          : infrastructure id
   * @return : InfrastructureStatus class with an internal map containing the
   *         outputs
   */
  public InfOutputValues getInfrastructureOutputs(String infId)
      throws ImClientException {
    String path = PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR
        + ImValues.OUTPUTS;
    return getImClient().get(path, InfOutputValues.class);
  }

}
