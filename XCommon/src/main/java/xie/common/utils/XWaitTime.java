package xie.common.utils;

import java.util.Date;

public class XWaitTime {
	/** 开始计算时间 微妙 */
	private long startTime;

	/** 超时时间 微妙 */
	private long timeout;

	public XWaitTime(long timeout) {
		startTime = new Date().getTime();
		this.timeout = timeout;
	}

	/**
	 * 设置超时时间
	 * 
	 * @param timeout 微妙
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 重置时计算的起始时间设为当前时间
	 */
	public void resetNowtime() {
		startTime = new Date().getTime();
	}

	/**
	 * 当前是否超时了
	 * 
	 * @return
	 */
	public boolean isTimeout() {
		if (getPastTime() > timeout) {
			return true;
		}

		return false;
	}

	/**
	 * 经过时间
	 * 
	 * @return
	 */
	public long getPastTime() {
		return new Date().getTime() - startTime;
	}

}
