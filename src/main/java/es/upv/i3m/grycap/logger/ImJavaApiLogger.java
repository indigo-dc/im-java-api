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

public class ImJavaApiLogger extends GenericLogger {

	private static Logger logger = Logger.getLogger(ImJavaApiLogger.class);

	public static void debug(Class<?> clazz, String message) {
		debug(logger, clazz.getName(),message);
	}
	
	public static void info(Class<?> clazz, String message) {
		info(logger, message);
	}
	
	public static void warning(Class<?> clazz, String message) {
		warning(logger, message);
	}
	
	public static void severe(String name, Throwable e) {
		severe(logger, GenericLogger.getStackTrace(e));
	}

	public static void severe(Class<?> clazz, Throwable e) {
		severe(logger, clazz.getName(), GenericLogger.getStackTrace(e));
	}
	
	public static void severe(Class<?> clazz, String message) {
		severe(logger, clazz.getName(), message);
	}
}
