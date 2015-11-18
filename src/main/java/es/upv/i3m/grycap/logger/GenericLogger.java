/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.upv.i3m.grycap.logger;

import org.apache.log4j.Logger;

public abstract class GenericLogger {

    protected GenericLogger() {
    }

    /**
     * Shows not critical errors. I.e. Email address not found, permissions not
     * allowed for this user, ...
     * 
     * @param message
     */
    private static void warning(Logger logger, String message) {
        logger.warn(message);
    }

    /**
     * Shows not critical errors. I.e. Email address not found, permissions not
     * allowed for this user, ...
     * 
     * @param message
     */
    protected static void warning(Logger logger, String className, String message) {
        warning(logger, className + ": " + message);
    }

    /**
     * Events that have business meaning (i.e. creating category, deleting form,
     * ...). To follow user actions.
     * 
     * @param message
     */
    private static void info(Logger logger, String message) {
        logger.info(message);
    }

    /**
     * Events that have business meaning (i.e. creating category, deleting form,
     * ...). To follow user actions.
     * 
     * @param message
     */
    protected static void info(Logger logger, String className, String message) {
        info(logger, className + ": " + message);
    }

    /**
     * For following the trace of the execution. I.e. Knowing if the application
     * access to a method, opening database connection, etc.
     * 
     * @param message
     */
    protected static void debug(Logger logger, String className, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(className + ": " + message);
        }
    }

    /**
     * To log any not expected error that can cause application malfunctions.
     * I.e. couldn't open database connection, etc..
     * 
     * @param message
     */
    private static void severe(Logger logger, String message) {
        logger.error(message);
    }

    /**
     * To log any not expected error that can cause application malfunctions.
     * 
     * @param message
     */
    protected static void severe(Logger logger, String className, String message) {
        severe(logger, className + ": " + message);
    }

    /**
     * To log any not expected error that can cause application malfunctions.
     * 
     * @param message
     */
    protected static void severe(Logger logger, String className, Throwable throwable) {
        logger.error("Error in '" + className + "'", throwable);
    }
}
