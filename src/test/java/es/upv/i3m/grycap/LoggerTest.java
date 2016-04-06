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

package es.upv.i3m.grycap;

import es.upv.i3m.grycap.im.exceptions.AuthorizationFileException;
import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.junit.Test;

public class LoggerTest {

  private static final String TEST_MESSAGE = "Test Message";

  @Test
  public void testLoggerDebug() {
    ImJavaApiLogger.debug(this.getClass(), TEST_MESSAGE);
  }

  @Test
  public void testLoggerInfo() {
    ImJavaApiLogger.info(this.getClass(), TEST_MESSAGE);
  }

  @Test
  public void testLoggerWarning() {
    ImJavaApiLogger.warning(this.getClass(), TEST_MESSAGE);
  }

  @Test
  public void testLoggerSevereException() {
    ImJavaApiLogger.severe(this.getClass(),
        new AuthorizationFileException(TEST_MESSAGE));
  }

  @Test
  public void testLoggerSevereMessage() {
    ImJavaApiLogger.severe(this.getClass(), TEST_MESSAGE);
  }
}
