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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterTest extends ImTestWatcher {

  private static final String NAME = "test";
  private static final String VALUE = "value";

  @Test
  public void testParameterWithNameCreation() {
    Parameter p = new Parameter(NAME);
    Assert.assertEquals(NAME, p.getName());
    Assert.assertArrayEquals(new Object[0], p.getValues());
  }

  @Test
  public void testParameterWithNameAndValueCreation() {
    Parameter p = new Parameter(NAME, VALUE);
    Assert.assertEquals(NAME, p.getName());
    Object[] expected = new Object[] { VALUE };
    Assert.assertArrayEquals(expected, p.getValues());
  }

  @Test
  public void testParameterWithNameAndArrayValueCreation() {
    List<String> values = Arrays.asList(VALUE, VALUE, VALUE);
    Parameter p = new Parameter(NAME, values);
    Assert.assertEquals(NAME, p.getName());
    Assert.assertArrayEquals(values.toArray(), p.getValues());
  }

  @Test
  public void testParameterWithNameAndNullArrayValueCreation() {
    Parameter p = new Parameter(NAME, null);
    Assert.assertEquals(NAME, p.getName());
    Assert.assertArrayEquals(new Object[0], p.getValues());

    String className = p.getClass().getName();
    String errorMessage = "Null or empty list passed as parameter values";
    Assert.assertEquals(className + ": " + errorMessage, getLogOutput());
  }

  @Test
  public void testParameterWithNameAndEmptyArrayValueCreation() {
    Parameter p = new Parameter(NAME, new ArrayList<>());
    Assert.assertEquals(NAME, p.getName());
    Assert.assertArrayEquals(new Object[0], p.getValues());

    String className = p.getClass().getName();
    String errorMessage = "Null or empty list passed as parameter values";
    Assert.assertEquals(className + ": " + errorMessage, getLogOutput());
  }

}
