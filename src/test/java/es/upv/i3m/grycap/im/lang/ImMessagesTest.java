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

package es.upv.i3m.grycap.im.lang;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ImMessagesTest {

  /**
   * Try to build a private class.
   */
  @Test(expected = InvocationTargetException.class)
  public void testImMessagesBuilder() throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    Constructor<ImMessages> constructor =
        ImMessages.class.getDeclaredConstructor(new Class[0]);
    constructor.setAccessible(true);
    constructor.newInstance(new Object[0]);
  }

  @Test
  public void testMessages() {
    Assert.assertEquals("REST call with empty body content",
        ImMessages.INFO_EMPTY_BODY_CONTENT);
  }
}
