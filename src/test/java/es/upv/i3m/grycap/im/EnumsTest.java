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
import es.upv.i3m.grycap.im.exceptions.ImClientException;
import es.upv.i3m.grycap.im.exceptions.NoEnumFoundException;
import es.upv.i3m.grycap.im.rest.client.BodyContentType;

import org.junit.Assert;
import org.junit.Test;

public class EnumsTest extends ImTestWatcher {

  @Test(expected = NoEnumFoundException.class)
  public void testVmStates() throws ImClientException {
    if (!States.PENDING.equals(States.getEnumFromValue("pending"))) {
      Assert.fail();
    }
    States.getEnumFromValue("not_valid");
  }

  @Test(expected = NoEnumFoundException.class)
  public void testImValues() throws ImClientException {
    if (!ImValues.START.equals(ImValues.getEnumFromValue("start"))) {
      Assert.fail();
    }
    Assert.assertNull(ImValues.getEnumFromValue("not_valid"));
  }

  @Test(expected = NoEnumFoundException.class)
  public void testVmProperties() throws ImClientException {
    if (!VmProperties.CPU_ARCH
        .equals(VmProperties.getEnumFromValue("cpu.arch"))) {
      Assert.fail();
    }
    Assert.assertNull(VmProperties.getEnumFromValue("not_valid"));
  }

  @Test(expected = NoEnumFoundException.class)
  public void testRestApiBodyContentType() throws ImClientException {
    if (!BodyContentType.TOSCA.equals(
        BodyContentType.getEnumFromValue(BodyContentType.TOSCA.toString()))) {
      Assert.fail();
    }
    Assert.assertNull(BodyContentType.getEnumFromValue("not_valid"));
  }
}
