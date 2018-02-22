package xie.module.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class XLog {
	private static Logger log = LoggerFactory.getLogger(XLog.class);

	public static Logger getXLogger() {
		return log;
	}

	public static void info(String message) {
		log.info(message);
	}

	public static void info(String message, Throwable e) {
		log.info(message, e);
	}

	public static void info(String message, Object... args) {
		log.info(message, args);
	}


	private static Map<Object, Logger> logMap = new HashMap<>();

	public static Logger getLogger(Object obj) {
		Logger logger = logMap.get(obj);
		if (logger == null) {
			synchronized (logMap) {
				Class c = obj.getClass();
				if (obj instanceof Class) {
					c = (Class) obj;
				}
				logger = LoggerFactory.getLogger(c);
				logMap.put(obj, logger);
			}
		}

		return logger;
	}

	public static void info(Object obj, String message) {
		getLogger(obj).info(message);
	}

	public static void info(Object obj, String message, Throwable e) {
		getLogger(obj).info(message, e);
	}

	public static void info(Object obj, String message, Object... args) {
		getLogger(obj).info(message, args);
	}
}
