package es.upv.i3m.grycap.im.pojo;

import es.upv.i3m.grycap.ImTestWatcher;
import es.upv.i3m.grycap.im.exceptions.FileException;
import es.upv.i3m.grycap.im.exceptions.InfrastructureUuidNotFoundException;

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
  }

  @Test(expected = InfrastructureUuidNotFoundException.class)
  public void testInfrastructureUriExtractUuidNotFound()
      throws InfrastructureUuidNotFoundException {
    InfrastructureUri uri = new InfrastructureUri(
        "http://127.0.0.1:8800/infrastructures/a71d6e4c-11e6-aa50-f079596e5f16/vms/0/contmsg");
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
    Assert.assertEquals(true, error.is404Error());
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
