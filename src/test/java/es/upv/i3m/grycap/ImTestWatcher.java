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

import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.StringWriter;

/**
 * This class is used to log the names of the tests executed. It also redirects
 * the logger to an internal String instead of displaying the output in the
 * console during the execution of the test, so we can check the outputs of the
 * tests.
 */
public class ImTestWatcher {

  private static final StringWriter stringWriter = new StringWriter();
  private static final String STRING_APPENDER_NAME = "StringAppender";

  @Rule
  public TestRule watcher = new TestWatcher() {
    protected void starting(Description description) {
      ImJavaApiLogger.debug(ImTestWatcher.class,
          "Starting test '" + description.getMethodName() + "'");
    }
  };

  @Before
  public void setStringLogger() {
    removeAppenders();
    createStringAppender();
  }

  @After
  public void cleanLogger() {
    cleanStringBuffer();
    removeAppenders();
    createConsoleAppender();
  }

  /**
   * Instead of creating a new string writer we set the internal buffer of the
   * writer to 0.
   */
  private void cleanStringBuffer() {
    StringBuffer buf = stringWriter.getBuffer();
    buf.setLength(0);
  }

  private void createStringAppender() {
    WriterAppender appender = new WriterAppender();
    appender.setName(STRING_APPENDER_NAME);
    appender.setLayout(new PatternLayout());
    appender.activateOptions();
    appender.setWriter(stringWriter);
    Logger.getLogger(ImJavaApiLogger.class).addAppender(appender);
  }

  private void createConsoleAppender() {
    ConsoleAppender appender = new ConsoleAppender();
    appender.setName(STRING_APPENDER_NAME);
    appender.setLayout(new PatternLayout(
        "%-5p %d{yyyy-MM-dd HH:mm:ss.SSS 'GMT'Z} %c{1} [%t] - %m%n"));
    appender.activateOptions();
    appender.setThreshold(Level.DEBUG);
    Logger.getLogger(ImJavaApiLogger.class).addAppender(appender);
  }

  private void removeAppenders() {
    Logger.getLogger(ImJavaApiLogger.class).removeAllAppenders();
  }

  public String getLogOutput() {
    return stringWriter.toString().trim();
  }

}
