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

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.file.NoNullOrEmptyFile;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.exceptions.FileException;
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.exceptions.ToscaContentTypeNotSupportedException;
import es.upv.i3m.grycap.im.pojo.InfOutputValues;
import es.upv.i3m.grycap.im.pojo.InfrastructureState;
import es.upv.i3m.grycap.im.pojo.InfrastructureUri;
import es.upv.i3m.grycap.im.pojo.InfrastructureUris;
import es.upv.i3m.grycap.im.pojo.Property;
import es.upv.i3m.grycap.im.pojo.VirtualMachineInfo;
import es.upv.i3m.grycap.im.rest.client.BodyContentType;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InfrastructureManagerTest extends ImTestWatcher {

  private static InfrastructureManager im;
  private String infrastructureId;
  private static final String IM_DUMMY_PROVIDER_URL =
      "https://appsgrycap.i3m.upv.es:31443/im-dev";

  private static final String AUTH_FILE_PATH = "./src/test/resources/auth.dat";
  private static final String RADL_ALTER_VM_FILE_PATH =
      "./src/test/resources/radls/alter-vm.radl";
  private static final String RADL_JSON_ALTER_VM_FILE_PATH =
      "./src/test/resources/radls/alter-vm.json";
  private static final String TOSCA_FILE_PATH =
      "./src/test/resources/tosca/galaxy_tosca.yaml";
  private static final String TOSCA_EXTRA_NODE_FILE_PATH =
      "./src/test/resources/tosca/galaxy_tosca_2_nodes.yaml";

  private static final String VM_DEFAULT_ID = "0";

  private String getInfrastructureId() {
    return infrastructureId;
  }

  private void setInfrastructureId(String infrastructureId) {
    this.infrastructureId = infrastructureId;
  }

  private InfrastructureManager getIm() {
    return im;
  }

  /**
   * Check if the VM state is the same as the 'state' parameter.
   * 
   * @param vmId
   *          : id of the virtual machine
   * @param vmState
   *          : state of the virtual machine
   * @throws ImClientException
   *           : exception in the IM client
   */
  private void checkVmState(String vmId, States vmState)
      throws ImClientException {
    Property vmProperty =
        getIm().getVmProperty(getInfrastructureId(), vmId, VmProperties.STATE);
    Assert.assertEquals(vmState.toString(), vmProperty.getValue());
  }

  /**
   * Creates a new rest client.
   */
  @BeforeClass
  public static void setRestClient() {
    try {
      im = new InfrastructureManager(IM_DUMMY_PROVIDER_URL,
          Paths.get(AUTH_FILE_PATH));
      im.setConnectTimeout(100000);
      im.setReadTimeout(900000);
    } catch (ImClientException exception) {
      ImJavaApiLogger.severe(InfrastructureManagerTest.class,
          exception.getMessage());
      Assert.fail();
    }
  }

  /**
   * Creates a new infrastructure.
   * 
   * @throws ImClientException
   */
  @Before
  public void createInfrastructure() throws ImClientException {
    InfrastructureUri newInfrastructureUri = getIm()
        .createInfrastructure(readFile(TOSCA_FILE_PATH), BodyContentType.TOSCA);
    String uri = newInfrastructureUri.getUri();
    Assert.assertEquals(false, uri.isEmpty());
    setInfrastructureId(newInfrastructureUri.getInfrastructureId());
  }

  private String readFile(String filePath) throws FileException {
    return new NoNullOrEmptyFile(new Utf8File(Paths.get(filePath))).read();
  }

  @After
  public void destroyInfrastructure() throws ImClientException {
    getIm().destroyInfrastructure(getInfrastructureId());
  }

  @Test
  public void testCreateAndDestroyInfrastructure() {
    // Create empty test to check the @Before and @After methods
  }
  
  @Test
  public void testCreateAndDestroyAsyncInfrastructure() throws ImClientException {
    InfrastructureUri newInfrastructureUri = getIm().createInfrastructureAsync(
    		readFile(TOSCA_FILE_PATH), BodyContentType.TOSCA);
    String uri = newInfrastructureUri.getUri();
    Assert.assertEquals(false, uri.isEmpty());
    String infId = newInfrastructureUri.getInfrastructureId();
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
    }
    getIm().destroyInfrastructure(infId);
  }

  @Test
  public void testInfrastructuresList() throws ImClientException {
    InfrastructureUris infUris = getIm().getInfrastructureList();
    Assert.assertEquals(false, infUris.getUris().isEmpty());
  }

  @Test
  public void testInfrastructureInfo() throws ImClientException {
    String expected = IM_DUMMY_PROVIDER_URL + "/infrastructures/"
        + getInfrastructureId() + "/vms/0";
    InfrastructureUris infUris =
        getIm().getInfrastructureInfo(getInfrastructureId());

    Assert.assertEquals(1, infUris.getUris().size());
    InfrastructureUri actualUri = infUris.getUris().get(0);
    Assert.assertEquals(expected, actualUri.getUri());
  }

  @Test
  public void testGetVmInfo() throws ImClientException {
    VirtualMachineInfo vmInfo =
        getIm().getVmInfo(getInfrastructureId(), VM_DEFAULT_ID);
    List<Map<String, Object>> vmProperties = vmInfo.getVmProperties();

    // Check the value of the first map
    Map<?, ?> info = (Map<String, Object>) vmProperties.get(0);
    Assert.assertEquals("no", info.get("outbound"));

    // Check an internal map inside the map
    Map<?, ?> internalInfo = (Map<String, Object>) vmProperties.get(2);
    List<?> applications = (List<?>) internalInfo.get("disk.0.applications");
    Map<?, ?> applicationsInfo = (Map<?, ?>) applications.get(0);
    Assert.assertEquals("ansible.modules.indigo-dc.galaxycloud",
        applicationsInfo.get("name"));
  }

  @Test
  public void testGetVmProperty() throws ImClientException {
    // Check for running state because the dummy provider never reaches the
    // 'configured' state
    checkVmState(VM_DEFAULT_ID, States.RUNNING);
  }

  @Test
  public void testGetInfrastructureContMsg()
      throws ImClientException, InterruptedException {
    Thread.sleep(10000);
    Property contextMessage =
        getIm().getInfrastructureContMsg(getInfrastructureId());
    // System.out.println(contextMessage.getValue());
    Assert.assertEquals(false, contextMessage.getValue().isEmpty());
  }

  @Test
  public void testGetInfrastructureRadl() throws ImClientException {
    Property infrRadl = getIm().getInfrastructureRadl(getInfrastructureId());
    Assert.assertEquals(false, infrRadl.getValue().isEmpty());
  }

  @Test
  public void testGetInfrastructureState() throws ImClientException {
    getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);

    InfrastructureState infState =
        getIm().getInfrastructureState(getInfrastructureId());

    Assert.assertEquals("running", infState.getState());
    String vm0State = infState.getVmStates().get("0");
    Assert.assertEquals("running", vm0State);
    String vm1State = infState.getVmStates().get("1");
    Assert.assertEquals("running", vm1State);
  }

  @Test
  public void testInfrastructureOutputs() throws ImClientException {
    String infId = getInfrastructureId();
    InfOutputValues result = getIm().getInfrastructureOutputs(infId);
    Map<String, Object> outputs = result.getOutputs();
    String galaxyUrl = (String) outputs.get("galaxy_url");
    Assert.assertEquals("http://10.0.0.1/galaxy", galaxyUrl);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testInfrastructureNestedOutputs() throws ImClientException {
    String infId = getInfrastructureId();
    InfOutputValues result = getIm().getInfrastructureOutputs(infId);
    Map<String, Object> outputs = result.getOutputs();
    Map<String, Object> credentials =
        (Map<String, Object>) outputs.get("cluster_creds");
    String user = (String) credentials.get("user");
    String token = (String) credentials.get("token");
    Assert.assertEquals("username", user);
    Assert.assertEquals("password", token);
  }

  @Test
  public void testAddResourceNoContext() throws ImClientException {
    InfrastructureUris infUris = getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);

    Assert.assertEquals(1, infUris.getUris().size());
    Assert.assertEquals(false, infUris.getUris().get(0).getUri().isEmpty());
  }

  @Test
  public void testAddResourceContextTrue() throws ImClientException {
    // Add a new resource
    InfrastructureUris infUris = getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA, true);

    Assert.assertEquals(1, infUris.getUris().size());
    Assert.assertEquals(false, infUris.getUris().get(0).getUri().isEmpty());
  }

  @Test
  public void testAddResourceContextFalse() throws ImClientException {
    InfrastructureUris infUris = getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA, false);

    Assert.assertEquals(1, infUris.getUris().size());
    Assert.assertEquals(false, infUris.getUris().get(0).getUri().isEmpty());
  }

  @Test
  public void testAddResourceContext() throws ImClientException {
    InfrastructureUris infUris =
        getIm().addResourceAndContextualize(getInfrastructureId(),
            readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);

    Assert.assertEquals(1, infUris.getUris().size());
    Assert.assertEquals(false, infUris.getUris().get(0).getUri().isEmpty());
  }

  @Test
  public void testAddResourceWithAllParams() throws ImClientException {
    InfrastructureUris infUris = getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA, true,
        true);

    Assert.assertEquals(1, infUris.getUris().size());
    Assert.assertEquals(false, infUris.getUris().get(0).getUri().isEmpty());
  }

  @Test
  public void testRemoveResourceNoContext() throws ImClientException {
    getIm().removeResource(getInfrastructureId(), VM_DEFAULT_ID);
  }

  @Test
  public void testRemoveResourceContextTrue() throws ImClientException {
    getIm().removeResource(getInfrastructureId(), VM_DEFAULT_ID, true);
  }

  @Test
  public void testRemoveResourceContextFalse() throws ImClientException {
    getIm().removeResource(getInfrastructureId(), VM_DEFAULT_ID, false);
  }

  @Test
  public void testStopInfrastructure() throws ImClientException {
    getIm().stopInfrastructure(getInfrastructureId());
  }

  @Test
  public void testStartInfrastructure() throws ImClientException {
    getIm().stopInfrastructure(getInfrastructureId());
    checkVmState(VM_DEFAULT_ID, States.STOPPED);
    getIm().startInfrastructure(getInfrastructureId());
    checkVmState(VM_DEFAULT_ID, States.RUNNING);
  }

  @Test
  public void testStopVm() throws ImClientException {
    getIm().stopVm(getInfrastructureId(), VM_DEFAULT_ID);
    checkVmState(VM_DEFAULT_ID, States.STOPPED);
  }

  @Test
  public void testStartVm() throws ImClientException {
    getIm().stopVm(getInfrastructureId(), VM_DEFAULT_ID);
    checkVmState(VM_DEFAULT_ID, States.STOPPED);
    getIm().startVm(getInfrastructureId(), VM_DEFAULT_ID);
    checkVmState(VM_DEFAULT_ID, States.RUNNING);
  }

  @Test(expected = ToscaContentTypeNotSupportedException.class)
  public void testAlterVmToscaContent() throws ImClientException, IOException {
    getIm().alterVm(getInfrastructureId(), VM_DEFAULT_ID,
        readFile(RADL_ALTER_VM_FILE_PATH), BodyContentType.TOSCA);
  }

  @Test
  public void testAlterVmRadlContent() throws ImClientException, IOException {
    VirtualMachineInfo vmInfo = getIm().alterVm(getInfrastructureId(),
        VM_DEFAULT_ID, readFile(RADL_ALTER_VM_FILE_PATH), BodyContentType.RADL);
    Assert.assertEquals(false, vmInfo.getVmProperties().isEmpty());

    Property cpuCount = getIm().getVmProperty(getInfrastructureId(),
        VM_DEFAULT_ID, VmProperties.CPU_COUNT);
    Assert.assertEquals("2", cpuCount.getValue());
  }

  @Test
  public void testAlterVmJsonRadlContent()
      throws ImClientException, IOException {
    VirtualMachineInfo vmInfo =
        getIm().alterVm(getInfrastructureId(), VM_DEFAULT_ID,
            readFile(RADL_JSON_ALTER_VM_FILE_PATH), BodyContentType.RADL_JSON);
    Assert.assertEquals(false, vmInfo.getVmProperties().isEmpty());

    Property cpuCount = getIm().getVmProperty(getInfrastructureId(),
        VM_DEFAULT_ID, VmProperties.CPU_COUNT);

    Assert.assertEquals("2", cpuCount.getValue());
  }

  @Test
  public void testReconfigureAllVms() throws ImClientException {
    // With the dummy provider the configured state is never reached
    getIm().reconfigure(getInfrastructureId());
  }

  @Test
  public void testReconfigureAllVmsUsingRadl()
      throws ImClientException, IOException, InterruptedException {
    // With the dummy provider the configured state is never reached
    getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);
    getIm().reconfigure(getInfrastructureId(),
        readFile(RADL_ALTER_VM_FILE_PATH), BodyContentType.RADL);
  }

  @Test
  public void testReconfigureAllVmsUsingRadlJson()
      throws ImClientException, IOException {
    // With the dummy provider the configured state is never reached
    getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);
    getIm().reconfigure(getInfrastructureId(),
        readFile(RADL_JSON_ALTER_VM_FILE_PATH), BodyContentType.RADL_JSON);
  }

  @Test
  public void testReconfigureSomeVmsRadl()
      throws ImClientException, IOException {
    getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);
    getIm().reconfigure(getInfrastructureId(),
        readFile(RADL_ALTER_VM_FILE_PATH), BodyContentType.RADL,
        Arrays.asList(0, 1));
  }

  @Test
  public void testReconfigureSomeVmsRadlJson()
      throws ImClientException, IOException {
    getIm().addResource(getInfrastructureId(),
        readFile(TOSCA_EXTRA_NODE_FILE_PATH), BodyContentType.TOSCA);
    getIm().reconfigure(getInfrastructureId(),
        readFile(RADL_JSON_ALTER_VM_FILE_PATH), BodyContentType.RADL_JSON,
        Arrays.asList(0, 1));
  }
}
