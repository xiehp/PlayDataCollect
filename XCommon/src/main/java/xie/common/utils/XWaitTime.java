package xie.common.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XWaitTime {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private long startTime;
	private long timeout;

	public XWaitTime(long timeout) {
		startTime = new Date().getTime();
		this.timeout = timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void resetNowtime() {
		startTime = new Date().getTime();
	}

	public boolean isTimeout() {
		if (new Date().getTime() - startTime > timeout) {
			return true;
		}

		return false;
	}

}
