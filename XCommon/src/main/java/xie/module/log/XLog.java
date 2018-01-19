package xie.module.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XLog{
	private static Logger log = LoggerFactory.getLogger(XLog.class);

	public static Logger getLog() {
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
}
