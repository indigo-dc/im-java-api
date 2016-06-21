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

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.im.States;
import es.upv.i3m.grycap.im.exceptions.FileException;
import es.upv.i3m.grycap.im.exceptions.InfrastructureUuidNotFoundException;
import es.upv.i3m.grycap.im.exceptions.NoEnumFoundException;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PojoTest extends ImTestWatcher {

  @Test
  public void testInfOutputValuesEquals() {
    Map<String, Object> outputValues = new HashMap<String, Object>();
    outputValues.put("test", "test");
    InfOutputValues outputs1 = new InfOutputValues(outputValues);
    InfOutputValues outputs2 = new InfOutputValues(outputValues);
    Assert.assertEquals(outputs1, outputs1);
    Assert.assertEquals(outputs1, outputs2);
    Assert.assertEquals(outputs1.hashCode(), outputs2.hashCode());
    Assert.assertFalse(outputs1.equals(""));
  }

  @Test
  public void testInfOutputValuesNotEquals() {
    Map<String, Object> outputValues = new HashMap<String, Object>();
    outputValues.put("test", "test");
    InfOutputValues outputs1 = new InfOutputValues(outputValues);
    InfOutputValues outputs3 = new InfOutputValues(null);
    Assert.assertNotEquals(outputs1, outputs3);
  }

  @Test
  public void testInfrastructureState() throws NoEnumFoundException {
    Map<String, String> infStates = new HashMap<String, String>();
    infStates.put("test", "test1");
    infStates.put("test2", "test3");
    InfrastructureState infState =
        new InfrastructureState(States.PENDING.getValue(), infStates);

    Assert.assertEquals(States.PENDING, infState.getEnumState());
    Assert.assertEquals(625493770, infState.hashCode());
    Assert.assertFalse(infState.equals(""));
    String expected = "Infrastructure state 'pending'\n"
        + " - Virtual machine 'test2' state ' test3' - Virtual machine 'test' state ' test1'";
    Assert.assertEquals(expected,
        infState.getFormattedInfrastructureStateString());

  }

  @Test
  public void testInfrastructureStateEquals() {
    Map<String, String> infStates = new HashMap<String, String>();
    infStates.put("test", "test");
    InfrastructureState infState1 =
        new InfrastructureState("infState", infStates);
    InfrastructureState infState2 =
        new InfrastructureState("infState", infStates);
    Assert.assertEquals(infState1, infState1);
    Assert.assertEquals(infState1, infState2);
  }

  @Test
  public void testInfrastructureStateNotEquals() {
    Map<String, String> infStates = new HashMap<String, String>();
    infStates.put("test", "test");
    InfrastructureState infState1 =
        new InfrastructureState("infState", infStates);
    InfrastructureState infState2 = new InfrastructureState("infState", null);
    Assert.assertNotEquals(infState1, infState2);
  }

  @Test
  public void testInfrastructureUriEquals() {
    InfrastructureUri uri1 = new InfrastructureUri("uri");
    InfrastructureUri uri2 = new InfrastructureUri("uri");
    Assert.assertEquals(uri1, uri1);
    Assert.assertEquals(uri1, uri2);
    Assert.assertEquals(uri1.hashCode(), uri1.hashCode());
    Assert.assertFalse(uri1.equals(""));
  }

  @Test
  public void testInfrastructureUriNotEquals() {
    InfrastructureUri uri1 = new InfrastructureUri("uri");
    InfrastructureUri uri2 = new InfrastructureUri("different_uri");
    Assert.assertNotEquals(uri1, uri2);
  }

  @Test
  public void testInfrastructureUriExtractUuid()
      throws InfrastructureUuidNotFoundException {
    InfrastructureUri uri = new InfrastructureUri(
        "http://127.0.0.1:8800/infrastructures/a71d6e4c-085e-11e6-aa50-f079596e5f16/vms/0/contmsg");
    String uuid = uri.getInfrastructureId();
    Assert.assertEquals("a71d6e4c-085e-11e6-aa50-f079596e5f16", uuid);
    uri.getInfrastructureId();
  }

  @Test(expected = InfrastructureUuidNotFoundException.class)
  public void testInfrastructureUriExtractUuidNotFound()
      throws InfrastructureUuidNotFoundException {
    // Uri not following the expected pattern
    InfrastructureUri uri =
        new InfrastructureUri("http://127.0.0.1:8800/9596e5f16/vms/0/contmsg");
    uri.getInfrastructureId();
  }

  @Test
  public void testInfrastructureUrisEquals() {
    InfrastructureUris uris1 = new InfrastructureUris(Arrays
        .asList(new InfrastructureUri("uri"), new InfrastructureUri("uri")));
    InfrastructureUris uris2 = new InfrastructureUris(Arrays
        .asList(new InfrastructureUri("uri"), new InfrastructureUri("uri")));
    Assert.assertEquals(uris1, uris1);
    Assert.assertEquals(uris1, uris2);
    Assert.assertEquals(uris1.hashCode(), uris2.hashCode());
    Assert.assertFalse(uris1.equals(""));
  }

  @Test
  public void testInfrastructureUrisNotEquals() {
    InfrastructureUris uris1 = new InfrastructureUris(Arrays
        .asList(new InfrastructureUri("uri"), new InfrastructureUri("uri")));
    InfrastructureUris uris2 =
        new InfrastructureUris(Arrays.asList(new InfrastructureUri("uri"),
            new InfrastructureUri("different_uri")));
    Assert.assertNotEquals(uris1, uris2);
  }

  @Test
  public void testPropertyEquals() {
    Property prop1 = new Property("key", "value");
    Property prop2 = new Property("key", "value");
    Assert.assertEquals(prop1, prop1);
    Assert.assertEquals(prop1, prop2);
    Assert.assertEquals(prop1.hashCode(), prop2.hashCode());
    Assert.assertFalse(prop1.equals(""));
  }

  @Test
  public void testPropertyNotEquals() {
    Property prop1 = new Property("key", "value");
    Property prop2 = new Property("key", "dif_value");
    Assert.assertNotEquals(prop1, prop2);
  }

  @Test
  public void testErrorEquals() {
    ResponseError error1 = new ResponseError("message", 200);
    ResponseError error2 = new ResponseError("message", 200);
    Assert.assertEquals(error1, error1);
    Assert.assertEquals(error1, error2);
    Assert.assertEquals(error1.hashCode(), error2.hashCode());
    Assert.assertFalse(error1.equals(""));
  }

  @Test
  public void testErrorNotEqualsCode() {
    ResponseError error1 = new ResponseError("message", 200);
    ResponseError error2 = new ResponseError("message", 400);
    Assert.assertNotEquals(error1, error2);
  }

  @Test
  public void testErrorNotEqualsMessage() {
    ResponseError error1 = new ResponseError("message", 200);
    ResponseError error2 = new ResponseError("message_dif", 200);
    Assert.assertNotEquals(error1, error2);
  }

  @Test
  public void testError404() {
    ResponseError error = new ResponseError("message", 404);
    Assert.assertTrue(error.is404Error());
    error = new ResponseError("message", 405);
    Assert.assertFalse(error.is404Error());
  }

  @Test
  public void testVirtualMachineInfoEquals() throws FileException {
    Map<String, Object> property1 = new HashMap<String, Object>();
    property1.put("test1", "test2");
    Map<String, Object> property2 = new HashMap<String, Object>();
    property2.put("test3", "test4");
    VirtualMachineInfo vmInfo1 =
        new VirtualMachineInfo(Arrays.asList(property1, property2));

    Map<String, Object> property3 = new HashMap<String, Object>();
    property3.put("test1", "test2");
    Map<String, Object> property4 = new HashMap<String, Object>();
    property4.put("test3", "test4");
    VirtualMachineInfo vmInfo2 =
        new VirtualMachineInfo(Arrays.asList(property3, property4));

    Assert.assertEquals(vmInfo1, vmInfo1);
    Assert.assertEquals(vmInfo1, vmInfo2);
    Assert.assertEquals(vmInfo1.hashCode(), vmInfo2.hashCode());
    Assert.assertFalse(vmInfo1.equals(""));
  }

  @Test
  public void testVirtualMachineInfoNotEquals() throws FileException {
    Map<String, Object> property1 = new HashMap<String, Object>();
    property1.put("test1", "test2");
    Map<String, Object> property2 = new HashMap<String, Object>();
    property2.put("test3", "test4");
    VirtualMachineInfo vmInfo1 =
        new VirtualMachineInfo(Arrays.asList(property1, property2));

    Map<String, Object> property3 = new HashMap<String, Object>();
    property3.put("test1", "test2");
    Map<String, Object> property4 = new HashMap<String, Object>();
    property4.put("test5", "test6");
    VirtualMachineInfo vmInfo2 =
        new VirtualMachineInfo(Arrays.asList(property3, property4));

    Assert.assertNotEquals(vmInfo1, vmInfo2);
  }
}
