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

package es.upv.i3m.grycap.logger;

import org.apache.log4j.Logger;

public class ImJavaApiLogger {

  private static final Logger LOGGER = Logger.getLogger(ImJavaApiLogger.class);

  private ImJavaApiLogger() {
    // It is not allowed to instantiate this class
    throw new UnsupportedOperationException();
  }

  /**
   * For following the trace of the execution. I.e. Knowing if the application
   * access to a method, opening database connection, etc.
   * 
   * @param clazz
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  public static void debug(Class<?> clazz, String message) {
    debug(LOGGER, clazz.getName(), message);
  }

  /**
   * For following the trace of the execution. I.e. Knowing if the application
   * access to a method, opening database connection, etc.
   * 
   * @param logger
   *          : Logger used for logging
   * @param className
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  private static void debug(Logger logger, String className, String message) {
    if (logger.isDebugEnabled()) {
      logger.debug(className + ": " + message);
    }
  }

  /**
   * Events that have business meaning (i.e. creating category, deleting form,
   * ...). To follow user actions.
   * 
   * @param clazz
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  public static void info(Class<?> clazz, String message) {
    info(LOGGER, clazz.getName(), message);
  }

  /**
   * Events that have business meaning (i.e. creating category, deleting form,
   * ...). To follow user actions.
   * 
   * @param logger
   *          : Logger used for logging
   * @param message
   *          : Message to log
   */
  private static void info(Logger logger, String message) {
    logger.info(message);
  }

  /**
   * Events that have business meaning (i.e. creating category, deleting form,
   * ...). To follow user actions.
   * 
   * @param logger
   *          : Logger used for logging
   * @param className
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  private static void info(Logger logger, String className, String message) {
    info(logger, className + ": " + message);
  }

  /**
   * To log any not expected error that can cause application malfunctions. I.e.
   * couldn't open database connection, etc..
   * 
   * @param clazz
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  public static void severe(Class<?> clazz, String message) {
    severe(LOGGER, clazz.getName(), message);
  }

  /**
   * To log any not expected error that can cause application malfunctions. I.e.
   * couldn't open database connection, etc..
   * 
   * @param clazz
   *          : Class generating the message
   * @param throwable
   *          : Exception raised
   */
  public static void severe(Class<?> clazz, Throwable throwable) {
    severe(LOGGER, clazz.getName(), throwable);
  }

  /**
   * To log any not expected error that can cause application malfunctions. I.e.
   * couldn't open database connection, etc..
   * 
   * @param logger
   *          : Logger used for logging
   * @param message
   *          : Message to log
   */
  private static void severe(Logger logger, String message) {
    logger.error(message);
  }

  /**
   * To log any not expected error that can cause application malfunctions.
   * 
   * @param logger
   *          : Logger used for logging
   * @param className
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  private static void severe(Logger logger, String className, String message) {
    severe(logger, className + ": " + message);
  }

  /**
   * To log any not expected error that can cause application malfunctions.
   * 
   * @param logger
   *          : Logger used for logging
   * @param className
   *          : Class generating the message
   * @param throwable
   *          : Exception raised
   */
  private static void severe(Logger logger, String className,
      Throwable throwable) {
    logger.error("Error in '" + className + "'", throwable);
  }

  /**
   * Shows not critical errors. I.e. Email address not found, permissions not
   * allowed for this user, ...
   * 
   * @param clazz
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  public static void warning(Class<?> clazz, String message) {
    warning(LOGGER, clazz.getName(), message);
  }

  /**
   * Shows not critical errors. I.e. Email address not found, permissions not
   * allowed for this user, ...
   * 
   * @param logger
   *          : Logger used for logging
   * @param message
   *          : Message to log
   */
  private static void warning(Logger logger, String message) {
    logger.warn(message);
  }

  /**
   * Shows not critical errors. I.e. Email address not found, permissions not
   * allowed for this user, ...
   * 
   * @param logger
   *          : Logger used for logging
   * @param className
   *          : Class generating the message
   * @param message
   *          : Message to log
   */
  private static void warning(Logger logger, String className, String message) {
    warning(logger, className + ": " + message);
  }
}
