package xie.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class XLog {
	private static Logger log = LoggerFactory.getLogger(XLog.class);

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

	public static Logger getLog(Object logTarget) {
		Logger logger = logMap.get(logTarget);
		if (logger == null) {
			synchronized (logMap) {
				Class c;
				if (logTarget instanceof Class) {
					c = (Class) logTarget;
				} else {
					c = logTarget.getClass();
				}
				logger = LoggerFactory.getLogger(c);
				logMap.put(logTarget, logger);
			}
		}

		return logger;
	}

	public static void info(Object logTarget, String message) {
		getLog(logTarget).info(message);
	}

	public static void info(Object logTarget, String message, Throwable e) {
		getLog(logTarget).info(message, e);
	}

	public static void info(Object logTarget, String message, Object... args) {
		getLog(logTarget).info(message, args);
	}
}
