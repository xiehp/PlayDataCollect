package xie.common.utils.utils;

public class XWaitTime {
	/** 开始计算时间 微妙 */
	private long startTime;

	/** 超时时间 微妙 */
	private long timeout;

	/**
	 * @param timeout 超时时间 微妙
	 */
	public XWaitTime(long timeout) {
		startTime = System.currentTimeMillis();
		this.timeout = timeout;
	}

	/**
	 * 设置超时时间
	 * 
	 * @param timeout 微妙
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * 设置开始时间
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * 重置时计算的起始时间设为当前时间
	 */
	public void resetNowtime() {
		startTime = System.currentTimeMillis();
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
	 * 当前是否超时了
	 * 
	 * @return
	 */
	public boolean isTimeoutAndResetStartTime() {
		if (isTimeout()) {
			resetNowtime();
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
		return System.currentTimeMillis() - startTime;
	}

}
