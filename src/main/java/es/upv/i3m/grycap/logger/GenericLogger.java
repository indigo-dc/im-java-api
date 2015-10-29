package es.upv.i3m.grycap.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

public abstract class GenericLogger {

	/**
	 * Shows not critical errors. I.e. Email address not found, permissions not
	 * allowed for this user, ...
	 * 
	 * @param message
	 */
	public static void warning(Logger logger, String message) {
		logger.warn(message);
	}

	/**
	 * Shows not critical errors. I.e. Email address not found, permissions not
	 * allowed for this user, ...
	 * 
	 * @param message
	 */
	public static void warning(Logger logger, String className, String message) {
		logger.warn(className + ": " + message);
	}

	/**
	 * Events that have business meaning (i.e. creating category, deleting form,
	 * ...). To follow user actions.
	 * 
	 * @param message
	 */
	public static void info(Logger logger, String message) {
		logger.info(message);
	}

	/**
	 * Events that have business meaning (i.e. creating category, deleting form,
	 * ...). To follow user actions.
	 * 
	 * @param message
	 */
	public static void info(Logger logger, String className, String message) {
		info(logger, className + ": " + message);
	}

	/**
	 * For following the trace of the execution. I.e. Knowing if the application
	 * access to a method, opening database connection, etc.
	 * 
	 * @param message
	 */
	public static void debug(Logger logger, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	/**
	 * For following the trace of the execution. I.e. Knowing if the application
	 * access to a method, opening database connection, etc.
	 * 
	 * @param message
	 */
	public static void debug(Logger logger, String className, String message) {
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
	public static void severe(Logger logger, String message) {
		logger.error(message);
	}

	/**
	 * To log any not expected error that can cause application malfunctions.
	 * 
	 * @param message
	 */
	public static void severe(Logger logger, String className, String message) {
		severe(logger, className + ": " + message);
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}
}
