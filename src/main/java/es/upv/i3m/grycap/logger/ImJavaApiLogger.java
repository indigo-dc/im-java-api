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
