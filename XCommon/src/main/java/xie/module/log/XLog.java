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

	public static Logger getLogger(Object logTarget) {
		Logger logger = logMap.get(logTarget);
		if (logger == null) {
			synchronized (logMap) {
				Class c = logTarget.getClass();
				if (logTarget instanceof Class) {
					c = (Class) logTarget;
				}
				logger = LoggerFactory.getLogger(c);
				logMap.put(logTarget, logger);
			}
		}

		return logger;
	}

	public static void info(Object logTarget, String message) {
		getLogger(logTarget).info(message);
	}

	public static void info(Object logTarget, String message, Throwable e) {
		getLogger(logTarget).info(message, e);
	}

	public static void info(Object logTarget, String message, Object... args) {
		getLogger(logTarget).info(message, args);
	}
}
