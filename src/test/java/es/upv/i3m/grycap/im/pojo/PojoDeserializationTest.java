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

package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.file.Utf8File;
import es.upv.i3m.grycap.im.exceptions.FileException;
import es.upv.i3m.grycap.im.exceptions.InfrastructureUuidNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class PojoDeserializationTest extends ImTestWatcher {

  private static final String JSON_FILES_FOLDER = "./src/test/resources/json/";

  @Test
  @SuppressWarnings("unchecked")
  public void testInfOuputValuesDeserialization() throws JsonParseException,
      JsonMappingException, IOException, FileException {
    String jsonString = getFileContentAsString("infrastructure-ouputs.json");
    InfOutputValues result =
        new ObjectMapper().readValue(jsonString, InfOutputValues.class);
    Map<String, Object> outputs = result.getOutputs();

    String serverUrl = (String) outputs.get("galaxy_url");
    Assert.assertEquals("http://127.0.0.1:8080", serverUrl);

    Map<String, Object> credentials =
        (Map<String, Object>) outputs.get("cluster_creds");
    String user = (String) credentials.get("user");
    String token = (String) credentials.get("token");
    Assert.assertEquals("username", user);
    Assert.assertEquals("password", token);
  }

  @Test
  public void testInfrastructureStateDeserialization()
      throws JsonParseException, JsonMappingException, IOException,
      FileException {
    String jsonString = getFileContentAsString("infrastructure-state.json");
    InfrastructureState infState =
        new ObjectMapper().readValue(jsonString, InfrastructureState.class);

    Assert.assertEquals("configured", infState.getState());
    String vm0State = infState.getVmStates().get("0");
    Assert.assertEquals("configured", vm0State);
  }

  @Test
  public void testInfrastructureUriDeserialization()
      throws JsonParseException, JsonMappingException, IOException,
      FileException, InfrastructureUuidNotFoundException {
    String jsonString = getFileContentAsString("infrastructure-uri.json");
    InfrastructureUri infUri =
        new ObjectMapper().readValue(jsonString, InfrastructureUri.class);

    Assert.assertEquals(
        "http://127.0.0.1:8800/infrastructures/02a04d9e-0d36-11e6-a466-300000000002",
        infUri.getUri());
    Assert.assertEquals("02a04d9e-0d36-11e6-a466-300000000002",
        infUri.getInfrastructureId());
  }

  @Test
  public void testInfrastructureUrisDeserialization()
      throws JsonParseException, JsonMappingException, IOException,
      FileException, InfrastructureUuidNotFoundException {
    String jsonString = getFileContentAsString("infrastructure-uris.json");
    InfrastructureUris infUris =
        new ObjectMapper().readValue(jsonString, InfrastructureUris.class);

    Assert.assertEquals(
        new InfrastructureUri(
            "http://127.0.0.1:8800/infrastructures/02a04d9e-0d36-11e6-a466-300000000002"),
        infUris.getUris().get(0));
    Assert.assertEquals(
        new InfrastructureUri(
            "http://127.0.0.1:8800/infrastructures/0f915ff0-f023-11e5-a466-300000000002"),
        infUris.getUris().get(1));
  }

  @Test
  public void testPropertyDeserialization() throws JsonParseException,
      JsonMappingException, IOException, FileException {
    String jsonString = getFileContentAsString("property.json");
    Property property =
        new ObjectMapper().readValue(jsonString, Property.class);
    Assert.assertEquals("key", property.getKey());
    Assert.assertEquals("value", property.getValue());
  }

  @Test
  public void testResponseErrorDeserialization() throws JsonParseException,
      JsonMappingException, IOException, FileException {
    String jsonString = getFileContentAsString("response-error.json");
    ResponseError responseError =
        new ObjectMapper().readValue(jsonString, ResponseError.class);

    Assert.assertEquals("Not found: '/infrastructures/'",
        responseError.getMessage());
    Assert.assertEquals((Integer) 404, responseError.getCode());

    String expectedFormattedMessage =
        "Error 404: Not found: '/infrastructures/'";
    Assert.assertEquals(expectedFormattedMessage,
        responseError.getFormattedErrorMessage());
  }

  @Test
  public void testVirtualMachineInfoDeserialization() throws JsonParseException,
      JsonMappingException, IOException, FileException {
    String jsonString = getFileContentAsString("virtual-machine-info.json");
    VirtualMachineInfo virtualMachineInfo =
        new ObjectMapper().readValue(jsonString, VirtualMachineInfo.class);

    List<Map<String, Object>> vmProperties =
        virtualMachineInfo.getVmProperties();

    // Check the value of the first map
    Map<?, ?> netInfo = (Map<String, Object>) vmProperties.get(0);
    Assert.assertEquals("publica", netInfo.get("provider_id"));
    Assert.assertEquals("yes", netInfo.get("outbound"));

    // Check an internal map inside the map
    Map<?, ?> machineInfo = (Map<String, Object>) vmProperties.get(2);
    Assert.assertEquals("one://ramses.i3m.upv.es/95",
        machineInfo.get("disk.0.image.url"));
  }

  private String getFileContentAsString(String fileName) throws FileException {
    return new Utf8File(Paths.get(JSON_FILES_FOLDER + fileName)).read();
  }
}
