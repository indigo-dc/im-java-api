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
package es.upv.i3m.grycap.im.api;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilderException;

import es.upv.i3m.grycap.im.client.InfrastructureManagerRestClient;
import es.upv.i3m.grycap.im.client.RestCallParameter;
import es.upv.i3m.grycap.im.client.ServiceResponse;
import es.upv.i3m.grycap.im.exceptions.AuthFileNotFoundException;

/**
 * This class offers the user an API to communicate with the Infrastructure
 * Manager
 */
public class InfrastructureManagerApiClient {

    // Path fragments reused in almost all the calls
    private static final String PATH_INFRASTRUCTURES = "infrastructures";
    private static final String PATH_VMS = "vms";
    private static final String PATH_SEPARATOR = "/";
    // IM REST parameters
    private static final String REST_PARAMETER_NAME_CONTEXT = "context";
    private static final String REST_PARAMETER_NAME_VMLIST = "vm_list";

    // Rest client needed to make the calls to the IM services
    private InfrastructureManagerRestClient imClient;

    /**
     * Create a new IM client
     * 
     * @param targetUrl
     *            : the URL where the REST API of the IM is defined
     * @param authFilePath
     *            : the path where the authorization file is defined
     * @throws CredentialsTypeNotDefinedException
     * @throws AuthFileNotFoundException
     */
    public InfrastructureManagerApiClient(String targetUrl, String authFilePath) throws AuthFileNotFoundException {
        setImClient(new InfrastructureManagerRestClient(targetUrl, authFilePath));
    }

    private InfrastructureManagerRestClient getImClient() {
        return imClient;
    }

    private void setImClient(InfrastructureManagerRestClient imClient) {
        this.imClient = imClient;
    }

    /**
     * Return a list of URIs referencing the infrastructures associated to the
     * IM user.
     * 
     * @return : list of URIs
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getInfrastructureList() throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES, MediaType.TEXT_PLAIN);
    }

    /**
     * Return a list of URIs referencing the virtual machines associated to the
     * infrastructure with ID 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @return : list of URIs
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getInfrastructureInfo(String infId) throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId, MediaType.TEXT_PLAIN);
    }

    /**
     * Return information about the virtual machine with ID vmId associated to
     * the infrastructure with ID infId.<br>
     * The returned string is in RADL format.<br>
     * See more the details of the output in
     * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#getvminfo-xmlrpc">
     * GetVMInfo</a>
     * 
     * @param infId
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @return : RADL file
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getVMInfo(String infId, String vmId) throws AuthFileNotFoundException {
        return getImClient().get(
                PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS + PATH_SEPARATOR + vmId,
                MediaType.TEXT_PLAIN);
    }

    /**
     * Return property 'propertyName' from to the virtual machine with ID 'vmId'
     * associated to the infrastructure with ID 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @param propertyName
     *            : name of the property to retrieve from the virtual machine
     * @return : a string with the property information
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getVMProperty(String infId, String vmId, String propertyName)
            throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS
                + PATH_SEPARATOR + vmId + PATH_SEPARATOR + propertyName, MediaType.TEXT_PLAIN);
    }

    /**
     * Return property 'propertyName' from to the virtual machine with ID 'vmId'
     * associated to the infrastructure with ID 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @param vmProperty
     *            : VM property to retrieve from the virtual machine
     * @return : a string with the property information
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getVMProperty(String infId, String vmId, VmProperties vmProperty)
            throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS
                + PATH_SEPARATOR + vmId + PATH_SEPARATOR + vmProperty.toString(), MediaType.TEXT_PLAIN);
    }

    /**
     * Return the contextualization log associated to the infrastructure with ID
     * 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @return : contextualization log
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getInfrastructureContMsg(String infId) throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.CONTMSG,
                MediaType.TEXT_PLAIN);
    }

    /**
     * Return a string with the original RADL specified to create the
     * infrastructure with ID 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @return : original RADL of the infrastructure
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getInfrastructureRADL(String infId) throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.RADL,
                MediaType.TEXT_PLAIN);
    }

    /**
     * Return a string with the infrastructure state.
     * 
     * @param infId
     *            : infrastructure id
     * @return : string with the infrastructure state
     * @throws InvalidAuthFileException
     */
    public ServiceResponse getInfrastructureState(String infId) throws AuthFileNotFoundException {
        return getImClient().get(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.STATE,
                MediaType.TEXT_PLAIN);
    }

    /**
     * Undeploy the virtual machines associated to the infrastructure with ID
     * 'infId'
     * 
     * @param infId
     *            : infrastructure id
     * @return : True if undeploy successful, false otherwise
     * @throws InvalidAuthFileException
     */
    public ServiceResponse destroyInfrastructure(String infId) throws AuthFileNotFoundException {
        return getImClient().delete(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId, MediaType.TEXT_PLAIN);
    }

    /**
     * Create and configure an infrastructure with the requirements specified in
     * the RADL document of the body contents.<br>
     * If success, it is returned the URI of the new infrastructure.
     * 
     * @param radlFile
     *            : file with the virtual machine properties and configuration
     * @return : URI of the new infrastructure
     * @throws InvalidAuthFileException
     */
    public ServiceResponse createInfrastructure(String radlFile) throws AuthFileNotFoundException {
        return getImClient().post(PATH_INFRASTRUCTURES, MediaType.TEXT_PLAIN, radlFile, MediaType.TEXT_PLAIN);
    }

    /**
     * Add the resources specified in the body contents to the infrastructure
     * with ID 'infId'. The RADL restrictions are the same as in
     * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#addresource-xmlrpc">
     * RPC-XML AddResource</a><br>
     * If success, it is returned a list of URIs of the new virtual machines.
     * <br>
     * The context parameter is optional and is a flag to specify if the
     * contextualization step will be launched just after the VM addition.<br>
     * As default the contextualization flag is set to True.
     * 
     * @param infId
     *            : infrastructure id
     * @param radlFile
     *            : file with the virtual machine properties and configuration
     * @param context
     *            : flag to specify if the contextualization step will be
     *            launched just after the VM addition
     * @return : list of URIs of the new virtual machines
     * 
     * @throws InvalidAuthFileException
     * @throws IncorrectContextInputException
     */
    public ServiceResponse addResource(String infId, String radlFile, boolean... context)
            throws AuthFileNotFoundException {
        if (context != null && context.length > 0) {
            return getImClient().post(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId, MediaType.TEXT_PLAIN, radlFile,
                    MediaType.TEXT_PLAIN, new RestCallParameter(REST_PARAMETER_NAME_CONTEXT, context[0]));
        } else {
            return getImClient().post(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId, MediaType.TEXT_PLAIN, radlFile,
                    MediaType.TEXT_PLAIN);
        }
    }

    /**
     * Undeploy the virtual machine with ID vmId associated to the
     * infrastructure with ID 'infId'.<br>
     * The context parameter is optional and is a flag to specify if the
     * contextualization step will be launched just after the VM addition.<br>
     * As default the contextualization flag is set to True.
     * 
     * @param infId
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @param context
     *            : flag to specify if the contextualization step will be
     *            launched just after the VM addition
     * @return : True if undeploy successful, false otherwise
     * @throws InvalidAuthFileException
     */
    public ServiceResponse removeResource(String infId, String vmId, boolean... context)
            throws AuthFileNotFoundException {
        if (context != null && context.length > 0) {
            return getImClient().delete(
                    PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS + PATH_SEPARATOR + vmId,
                    MediaType.TEXT_PLAIN, new RestCallParameter(REST_PARAMETER_NAME_CONTEXT, context[0]));
        } else {
            return getImClient().delete(
                    PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS + PATH_SEPARATOR + vmId,
                    MediaType.TEXT_PLAIN);
        }
    }

    /**
     * 
     * Stop (but do not undeploy) all the virtual machines associated to the
     * infrastructure with ID 'infId'. They can be resumed by the
     * 'startInfrastructure' method.
     * 
     * @param infId
     *            : infrastructure id
     * @return : True if stopping the infrastructure is successful, false
     *         otherwise
     * @throws InvalidAuthFileException
     */
    public ServiceResponse stopInfrastructure(String infId) throws AuthFileNotFoundException {
        return getImClient().put(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.STOP,
                MediaType.TEXT_PLAIN, "", MediaType.TEXT_PLAIN);
    }

    /**
     * Resume all the virtual machines associated to the infrastructure with ID
     * 'infId', previously stopped with the 'stopInfrastructure' method.
     * 
     * @param infId
     *            : infrastructure id
     * @return : True if starting the infrastructure is successful, false
     *         otherwise
     * @throws InvalidAuthFileException
     */
    public ServiceResponse startInfrastructure(String infId) throws AuthFileNotFoundException {
        return getImClient().put(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.START,
                MediaType.TEXT_PLAIN, "", MediaType.TEXT_PLAIN);
    }

    /**
     * Perform the 'stop' action in the virtual machine with ID 'vmId'
     * associated to the infrastructure with ID 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @return : True if stopping the vm is successful, false otherwise
     * @throws InvalidAuthFileException
     */
    public ServiceResponse stopVM(String infId, String vmId) throws AuthFileNotFoundException {
        return getImClient().put(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS
                + PATH_SEPARATOR + vmId + PATH_SEPARATOR + ImValues.STOP, MediaType.TEXT_PLAIN, "",
                MediaType.TEXT_PLAIN);
    }

    /**
     * Perform the 'start' action in the virtual machine with ID 'vmId'
     * associated to the infrastructure with ID 'infId'.
     * 
     * @param infId
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @return : True if starting the vm is successful, false otherwise
     * @throws InvalidAuthFileException
     */
    public ServiceResponse startVM(String infId, String vmId) throws AuthFileNotFoundException {
        return getImClient().put(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS
                + PATH_SEPARATOR + vmId + PATH_SEPARATOR + ImValues.START, MediaType.TEXT_PLAIN, "",
                MediaType.TEXT_PLAIN);
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
     *            : infrastructure id
     * @param vmId
     *            : virtual machine id
     * @param radlFile
     *            : file with the virtual machine properties and configuration
     * @return : RADL with information about the virtual machine modified
     * 
     * @throws InvalidAuthFileException
     */
    public ServiceResponse alterVM(String infId, String vmId, String radlFile) throws AuthFileNotFoundException {
        return getImClient().put(
                PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + PATH_VMS + PATH_SEPARATOR + vmId,
                MediaType.TEXT_PLAIN, radlFile, MediaType.TEXT_PLAIN);
    }

    /**
     * Perform the reconfigure action in all the virtual machines in the the
     * infrastructure with ID 'infID'.<br>
     * This method starts the contextualization process again.<br>
     * To reconfigure the VMs see the reconfigure methods that include the
     * 'radl' parameter.
     * 
     * @param infId
     *            : infrastructure id
     * @throws InvalidAuthFileException
     */
    public ServiceResponse reconfigure(String infId) throws AuthFileNotFoundException {
        return getImClient().put(PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.RECONFIGURE,
                MediaType.TEXT_PLAIN, "", MediaType.TEXT_PLAIN);
    }

    /**
     * Perform the reconfigure action in all the virtual machines in the the
     * infrastructure with ID 'infID.' It updates the configuration of the
     * infrastructure as indicated in the 'radlFile'. The RADL restrictions are
     * the same as in
     * <a href="http://www.grycap.upv.es/im/doc/xmlrpc.html#reconfigure-xmlrpc">
     * RPC-XML Reconfigure</a><br>
     * If no RADL are specified, the contextualization process is started again.
     * The 'vmList' parameter is optional and is a comma separated array of IDs
     * of the VMs to reconfigure. If not specified all the VMs will be
     * reconfigured.
     * 
     * @param infId
     *            : infrastructure id
     * @param radlFile
     *            : file with the infrastructure properties and configuration
     * @param vmList
     *            : comma separated list of IDs of the VMs to reconfigure
     * @throws AuthFileNotFoundException
     * @throws UriBuilderException
     * @throws IllegalArgumentException
     */
    public ServiceResponse reconfigure(String infId, String radlFile, int... vmList) throws AuthFileNotFoundException {
        String putContent = radlFile;
        if (putContent == null || putContent.isEmpty()) {
            putContent = "";
        }
        if (vmList != null && vmList.length > 0) {
            RestCallParameter parameters = new RestCallParameter(REST_PARAMETER_NAME_VMLIST);
            for (int vmId : vmList) {
                parameters.addValue(vmId);
            }
            return getImClient().put(
                    PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.RECONFIGURE,
                    MediaType.TEXT_PLAIN, putContent, MediaType.TEXT_PLAIN, parameters);
        } else {
            return getImClient().put(
                    PATH_INFRASTRUCTURES + PATH_SEPARATOR + infId + PATH_SEPARATOR + ImValues.RECONFIGURE,
                    MediaType.TEXT_PLAIN, putContent, MediaType.TEXT_PLAIN);
        }
    }
}
