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

package es.upv.i3m.grycap.im.rest.client.parameters;

import es.upv.i3m.grycap.ImTestWatcher;

import org.junit.Assert;
import org.junit.Test;

public class NoParameterTest extends ImTestWatcher {

  @Test
  public void testNoParameterCreation() {
    NoParameter np = new NoParameter();
    Assert.assertArrayEquals(new Object[0], np.getValues());
  }

  @Test
  public void testNoParameterName() {
    NoParameter np = new NoParameter();
    Assert.assertEquals("", np.getName());
  }

  @Test
  public void testNoParameterAddValue() {
    NoParameter np = new NoParameter();
    np.addValue("TEST");
    Assert.assertArrayEquals(new Object[0], np.getValues());
  }
}
