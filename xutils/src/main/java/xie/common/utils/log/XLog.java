package xie.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class XLog {
	private static Map<Object, Logger> logMap = new HashMap<>();

	public static Logger getLog(Object logTarget) {
		Logger logger = logMap.get(logTarget);
		if (logger == null) {
			synchronized (logMap) {
				logger = logMap.get(logTarget);
				if (logger == null) {
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
