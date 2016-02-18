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
package es.upv.i3m.grycap.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.upv.i3m.grycap.file.FileIO;
import es.upv.i3m.grycap.im.api.ImValues;
import es.upv.i3m.grycap.im.api.InfrastructureManagerApiClient;
import es.upv.i3m.grycap.im.api.InfrastructureStatus;
import es.upv.i3m.grycap.im.api.RestApiBodyContentType;
import es.upv.i3m.grycap.im.api.VmProperties;
import es.upv.i3m.grycap.im.api.VmStates;
import es.upv.i3m.grycap.im.client.ServiceResponse;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.exceptions.ToscaContentTypeNotSupportedException;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

public class InfrastructureManagerApiClientTest {

    // Client needed to connect to the java api
    private static InfrastructureManagerApiClient imApiClient;
    // IM connection urls
    private static final String IM_DUMMY_PROVIDER_URL = "http://servproject.i3m.upv.es:8811";
    // Authorization file path
    private static final String AUTH_FILE_PATH = "./src/test/resources/auth.dat";
    // IM RADLs
    private static final String RADL_ALTER_VM_FILE_PATH = "./src/test/resources/radls/alter-vm.radl";
    // IM RADLs JSON
    private static final String RADL_JSON_ALTER_VM_FILE_PATH = "./src/test/resources/radls/alter-vm.json";
    // IM TOSCA files
    private static final String TOSCA_FILE_PATH = "./src/test/resources/tosca/galaxy_tosca.yaml";
    private static final String TOSCA_EXTRA_NODE_FILE_PATH = "./src/test/resources/tosca/galaxy_tosca_2_nodes.yaml";
    // VM identifiers
    private static final String VM_DEFAULT_ID = "0";
    private static final String VM_ID_ONE = "1";
    // Max retries for rest calls
    private static final Integer MAX_RETRY = 20;
    // getInfrastructureOuputs expected result
    private static final String EXPECTED_INFRASTRUCTURE_OUTPUT_PRIVATE_IP_KEY = "private_ip";
    private static final String EXPECTED_INFRASTRUCTURE_OUTPUT_PRIVATE_IP_VALUE = "10.0.0.1";
    // ID of the infrastructure created
    private String infrastructureId;

    private String getInfrastructureId() {
        return infrastructureId;
    }

    private void setInfrastructureId(String infrastructureId) {
        this.infrastructureId = infrastructureId;
    }

    private InfrastructureManagerApiClient getImApiClient() {
        return imApiClient;
    }

    private void waitUntilRunningOrUncofiguredState(String vmId) throws ImClientException {
        while (true) {
            String vmState = getImApiClient().getVMProperty(getInfrastructureId(), vmId, VmProperties.STATE, false)
                    .getResult();
            if (VmStates.RUNNING.toString().equals(vmState) || VmStates.UNCONFIGURED.toString().equals(vmState)) {
                break;
            }
        }
    }

    /**
     * Fail the test if the string is null or empty
     * 
     * @param s
     */
    private void checkStringHasContent(String s) {
        if (s == null || s.isEmpty()) {
            Assert.fail();
        }
    }

    /**
     * Fail the test if the response is not successful
     * 
     * @param response
     * @throws InterruptedException
     */
    private void checkServiceResponse(ServiceResponse response) {
        if (!response.isReponseSuccessful()) {
            ImJavaApiLogger.severe(this.getClass(), response.getResult());
            Assert.fail();
        }
    }

    /**
     * Check if the VM state is the same as the 'state' parameter
     * 
     * @param vmId
     * @param vmState
     * @throws ImClientException
     */
    private void checkVMState(String vmId, VmStates vmState) throws ImClientException {
        ServiceResponse response = getImApiClient().getVMProperty(getInfrastructureId(), vmId, VmProperties.STATE,
                false);
        checkServiceResponse(response);
        if (!vmState.toString().equals(response.getResult())) {
            Assert.fail();
        }
    }

    private ServiceResponse addToscaResource(String toscaFilePath) throws ImClientException {
        ServiceResponse response = null;
        try {
            response = getImApiClient().addResource(getInfrastructureId(), FileIO.readUTF8File(toscaFilePath),
                    RestApiBodyContentType.TOSCA);
        } catch (IOException e) {
            Assert.fail();
        }
        return response;
    }

    @BeforeClass
    public static void setRestClient() {
        try {
            imApiClient = new InfrastructureManagerApiClient(IM_DUMMY_PROVIDER_URL, AUTH_FILE_PATH);
        } catch (ImClientException e) {
            ImJavaApiLogger.severe(InfrastructureManagerApiClientTest.class, e.getMessage());
            Assert.fail();
        }
    }

    @Before
    public void createInfrastructure() throws ImClientException {
        try {
            ServiceResponse response = getImApiClient().createInfrastructure(FileIO.readUTF8File(TOSCA_FILE_PATH),
                    RestApiBodyContentType.TOSCA);

            checkServiceResponse(response);
            String[] parsedURI = response.getResult().split("/");
            // Get the last element which is the infId
            setInfrastructureId(parsedURI[parsedURI.length - 1]);
        } catch (IOException e) {
            ImJavaApiLogger.severe(InfrastructureManagerApiClientTest.class, e.getMessage());
            Assert.fail();
        }
    }

    @After
    public void destroyInfrastructure() throws ImClientException {
        checkServiceResponse(getImApiClient().destroyInfrastructure(getInfrastructureId()));
    }

    @Test
    public void testCreateAndDestroyInfrastructure() {
        // Functionality tested before and after each test
    }

    @Test
    public void testInfrasturesList() throws ImClientException {
        checkServiceResponse(getImApiClient().getInfrastructureList());
    }

    @Test
    public void testInfrastructureInfo() throws ImClientException {
        String test = IM_DUMMY_PROVIDER_URL + "/infrastructures/" + getInfrastructureId() + "/vms/0";
        ServiceResponse response = getImApiClient().getInfrastructureInfo(getInfrastructureId());
        checkServiceResponse(response);
        if (!response.getResult().equals(test)) {
            Assert.fail();
        }
    }

    @Test
    public void testGetVMInfoRequestJson() throws ImClientException {
        ServiceResponse response = getImApiClient().getVMInfo(getInfrastructureId(), VM_DEFAULT_ID, true);
        checkServiceResponse(response);
        checkStringHasContent(response.getResult());
    }

    @Test
    public void testGetVMInfoRequestString() throws ImClientException {
        ServiceResponse response = getImApiClient().getVMInfo(getInfrastructureId(), VM_DEFAULT_ID, false);
        checkServiceResponse(response);
        checkStringHasContent(response.getResult());
    }

    @Test
    public void testGetVMProperty() throws ImClientException {
        checkVMState(VM_DEFAULT_ID, VmStates.RUNNING);
    }

    @Test
    public void testGetInfrastructureContMsg() throws ImClientException {
        ServiceResponse response = getImApiClient().getInfrastructureContMsg(getInfrastructureId());
        checkServiceResponse(response);
    }

    @Test
    public void testGetInfrastructureRADL() throws ImClientException {
        ServiceResponse response = getImApiClient().getInfrastructureRADL(getInfrastructureId());
        checkServiceResponse(response);
        checkStringHasContent(response.getResult());
    }

    @Test
    public void testGetInfrastructureState() throws ImClientException {
        ServiceResponse response = getImApiClient().getInfrastructureState(getInfrastructureId());
        checkServiceResponse(response);
        checkStringHasContent(response.getResult());
    }

    @Test
    public void testAddResourceNoContext() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Add a new resource
        ServiceResponse response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
        int retry;
        for (retry = 0; retry < MAX_RETRY; retry++) {
            response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
            if (response.isReponseSuccessful()) {
                waitUntilRunningOrUncofiguredState(VM_ID_ONE);
                break;
            }
        }
        if (retry == MAX_RETRY) {
            Assert.fail();
        }
        checkServiceResponse(response);
    }

    @Test
    public void testAddResourceContextTrue() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Add a new resource
        ServiceResponse response = addToscaResource(TOSCA_FILE_PATH);
        int retry;
        for (retry = 0; retry < MAX_RETRY; retry++) {
            response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
            if (response.isReponseSuccessful()) {
                waitUntilRunningOrUncofiguredState(VM_ID_ONE);
                break;
            }
        }
        if (retry == MAX_RETRY) {
            Assert.fail();
        }
        checkServiceResponse(response);
    }

    @Test
    public void testAddResourceContextFalse() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Add a new resource
        ServiceResponse response = addToscaResource(TOSCA_FILE_PATH);
        int retry;
        for (retry = 0; retry < MAX_RETRY; retry++) {
            response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
            if (response.isReponseSuccessful()) {
                waitUntilRunningOrUncofiguredState(VM_ID_ONE);
                break;
            }
        }
        if (retry == MAX_RETRY) {
            Assert.fail();
        }
        checkServiceResponse(response);
    }

    @Test
    public void testRemoveResourceNoContext() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().removeResource(getInfrastructureId(), VM_DEFAULT_ID);
        checkServiceResponse(response);
    }

    @Test
    public void testRemoveResourceContextTrue() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().removeResource(getInfrastructureId(), VM_DEFAULT_ID, true);
        checkServiceResponse(response);
    }

    @Test
    public void testRemoveResourceContextFalse() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().removeResource(getInfrastructureId(), VM_DEFAULT_ID, false);
        checkServiceResponse(response);
    }

    @Test
    public void testStopInfrastructure() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().stopInfrastructure(getInfrastructureId());
        checkServiceResponse(response);
        checkVMState(VM_DEFAULT_ID, VmStates.STOPPED);
    }

    @Test
    public void testStartInfrastructure() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().stopInfrastructure(getInfrastructureId());
        checkServiceResponse(response);
        checkVMState(VM_DEFAULT_ID, VmStates.STOPPED);
        response = getImApiClient().startInfrastructure(getInfrastructureId());
        checkServiceResponse(response);
        checkVMState(VM_DEFAULT_ID, VmStates.RUNNING);
    }

    @Test
    public void testStopVM() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().stopVM(getInfrastructureId(), VM_DEFAULT_ID);
        checkServiceResponse(response);
        checkVMState(VM_DEFAULT_ID, VmStates.STOPPED);
    }

    @Test
    public void testStartVM() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().stopVM(getInfrastructureId(), VM_DEFAULT_ID);
        checkServiceResponse(response);
        checkVMState(VM_DEFAULT_ID, VmStates.STOPPED);
        response = getImApiClient().startVM(getInfrastructureId(), VM_DEFAULT_ID);
        checkServiceResponse(response);
        checkVMState(VM_DEFAULT_ID, VmStates.RUNNING);
    }

    @Test(expected = ToscaContentTypeNotSupportedException.class)
    public void testAlterVMToscaContent() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Wait for the machine to be properly configured
        getImApiClient().alterVM(getInfrastructureId(), VM_DEFAULT_ID, FileIO.readUTF8File(RADL_ALTER_VM_FILE_PATH),
                RestApiBodyContentType.TOSCA, false);

        ServiceResponse response = getImApiClient().getVMProperty(getInfrastructureId(), VM_DEFAULT_ID,
                VmProperties.CPU_COUNT, false);
        checkServiceResponse(response);
        // Check that the alteration of the VM has been successful
        String cpuCount = response.getResult();
        if (cpuCount == null || cpuCount.isEmpty() || !cpuCount.equals("2")) {
            Assert.fail();
        }
    }

    @Test
    public void testAlterVMSimpleRadlNoJsonRequest() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Wait for the machine to be properly configured
        getImApiClient().alterVM(getInfrastructureId(), VM_DEFAULT_ID, FileIO.readUTF8File(RADL_ALTER_VM_FILE_PATH),
                RestApiBodyContentType.RADL, false);
        ServiceResponse response = getImApiClient().getVMProperty(getInfrastructureId(), VM_DEFAULT_ID,
                VmProperties.CPU_COUNT, false);
        checkServiceResponse(response);
        // Check that the alteration of the VM has been successful
        String cpuCount = response.getResult();
        if (cpuCount == null || cpuCount.isEmpty() || !cpuCount.equals("2")) {
            Assert.fail();
        }
    }

    @Test
    public void testAlterVMJsonRadlNoJsonRequest() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Wait for the machine to be properly configured
        getImApiClient().alterVM(getInfrastructureId(), VM_DEFAULT_ID,
                FileIO.readUTF8File(RADL_JSON_ALTER_VM_FILE_PATH), RestApiBodyContentType.RADL_JSON, false);
        ServiceResponse response = getImApiClient().getVMProperty(getInfrastructureId(), VM_DEFAULT_ID,
                VmProperties.CPU_COUNT, false);
        checkServiceResponse(response);
        // Check that the alteration of the VM has been successful
        String cpuCount = response.getResult();
        if (cpuCount == null || cpuCount.isEmpty() || !cpuCount.equals("2")) {
            Assert.fail();
        }
    }

    @Test
    public void testAlterVMSimpleRadlJsonRequest() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Wait for the machine to be properly configured
        getImApiClient().alterVM(getInfrastructureId(), VM_DEFAULT_ID, FileIO.readUTF8File(RADL_ALTER_VM_FILE_PATH),
                RestApiBodyContentType.RADL, true);
        ServiceResponse response = getImApiClient().getVMProperty(getInfrastructureId(), VM_DEFAULT_ID,
                VmProperties.CPU_COUNT, true);
        checkServiceResponse(response);
        // Check that the alteration of the VM has been successful
        String cpuCount = response.getResult();
        if (cpuCount == null || cpuCount.isEmpty() || !cpuCount.equals("2")) {
            Assert.fail();
        }
    }

    @Test
    public void testAlterVMJsonRadlJsonRequest() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        // Wait for the machine to be properly configured
        getImApiClient().alterVM(getInfrastructureId(), VM_DEFAULT_ID,
                FileIO.readUTF8File(RADL_JSON_ALTER_VM_FILE_PATH), RestApiBodyContentType.RADL_JSON, false);
        ServiceResponse response = getImApiClient().getVMProperty(getInfrastructureId(), VM_DEFAULT_ID,
                VmProperties.CPU_COUNT, true);
        checkServiceResponse(response);
        // Check that the alteration of the VM has been successful
        String cpuCount = response.getResult();
        if (cpuCount == null || cpuCount.isEmpty() || !cpuCount.equals("2")) {
            Assert.fail();
        }
    }

    @Test
    public void testReconfigure() throws ImClientException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = getImApiClient().reconfigure(getInfrastructureId());
        checkServiceResponse(response);
    }

    @Test
    public void testReconfigureAllVmsRadl() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
        checkServiceResponse(response);
        waitUntilRunningOrUncofiguredState(VM_ID_ONE);
        response = getImApiClient().reconfigure(getInfrastructureId(), FileIO.readUTF8File(RADL_ALTER_VM_FILE_PATH),
                RestApiBodyContentType.RADL);
        checkServiceResponse(response);
    }

    @Test
    public void testReconfigureAllVmsRadlJson() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
        checkServiceResponse(response);
        waitUntilRunningOrUncofiguredState(VM_ID_ONE);
        response = getImApiClient().reconfigure(getInfrastructureId(),
                FileIO.readUTF8File(RADL_JSON_ALTER_VM_FILE_PATH), RestApiBodyContentType.RADL_JSON);
        checkServiceResponse(response);
    }

    @Test
    public void testReconfigureSomeVmsRadl() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
        checkServiceResponse(response);
        waitUntilRunningOrUncofiguredState(VM_ID_ONE);
        response = getImApiClient().reconfigure(getInfrastructureId(), FileIO.readUTF8File(RADL_ALTER_VM_FILE_PATH),
                RestApiBodyContentType.RADL, Arrays.asList(0, 1));
        checkServiceResponse(response);
    }

    @Test
    public void testReconfigureSomeVmsRadlJson() throws ImClientException, IOException {
        waitUntilRunningOrUncofiguredState(VM_DEFAULT_ID);
        ServiceResponse response = addToscaResource(TOSCA_EXTRA_NODE_FILE_PATH);
        checkServiceResponse(response);
        waitUntilRunningOrUncofiguredState(VM_ID_ONE);
        response = getImApiClient().reconfigure(getInfrastructureId(),
                FileIO.readUTF8File(RADL_JSON_ALTER_VM_FILE_PATH), RestApiBodyContentType.RADL_JSON,
                Arrays.asList(0, 1));
        checkServiceResponse(response);
    }

    @Test
    public void testServiceResponse() throws ImClientException {
        ServiceResponse response = getImApiClient().getInfrastructureList();
        Assert.assertFalse(response.toString().isEmpty());
        Assert.assertFalse(response.getResult().isEmpty());
        Assert.assertEquals(response.getServiceStatusCode(), 200);
        Assert.assertEquals(response.getServiceStatusInfo(), Status.OK);
        Assert.assertEquals(response.getReasonPhrase(), "OK");
        Assert.assertEquals(response.isReponseSuccessful(), true);
    }

    @Test
    public void testServiceResponseUnsuccessfulRequestJson() throws ImClientException {
        ServiceResponse response = getImApiClient().getVMInfo("", VM_DEFAULT_ID, true);
        Assert.assertEquals(response.isReponseSuccessful(), false);
    }

    @Test
    public void testServiceResponseUnsuccessfulRequestString() throws ImClientException {
        ServiceResponse response = getImApiClient().getVMInfo("", VM_DEFAULT_ID, false);
        Assert.assertEquals(response.isReponseSuccessful(), false);
    }

    @Test
    public void testEnums() throws ImClientException {
        if (!VmStates.PENDING.equals(VmStates.getEnumFromValue("pending"))) {
            Assert.fail();
        }
        Assert.assertNull(VmStates.getEnumFromValue("not_valid"));

        if (!ImValues.START.equals(ImValues.getEnumFromValue("start"))) {
            Assert.fail();
        }
        Assert.assertNull(ImValues.getEnumFromValue("not_valid"));

        if (!VmProperties.CPU_ARCH.equals(VmProperties.getEnumFromValue("cpu.arch"))) {
            Assert.fail();
        }
        Assert.assertNull(VmProperties.getEnumFromValue("not_valid"));

        if (!RestApiBodyContentType.TOSCA
                .equals(RestApiBodyContentType.getEnumFromValue(RestApiBodyContentType.TOSCA.toString()))) {
            Assert.fail();
        }
        Assert.assertNull(RestApiBodyContentType.getEnumFromValue("not_valid"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInfrastructureOutputs() throws ImClientException {
        InfrastructureManagerApiClient client = getImApiClient();
        String infId = getInfrastructureId();
        InfrastructureStatus result = client.getInfrastructureOutputs(infId);
        Map<String, Object> properties = result.getProperties();
        System.out.println(properties.get(EXPECTED_INFRASTRUCTURE_OUTPUT_PRIVATE_IP_KEY));
        ArrayList<String> privateIps = (ArrayList<String>) properties
                .get(EXPECTED_INFRASTRUCTURE_OUTPUT_PRIVATE_IP_KEY);
        Assert.assertEquals(EXPECTED_INFRASTRUCTURE_OUTPUT_PRIVATE_IP_VALUE, privateIps.get(0));
    }
}
