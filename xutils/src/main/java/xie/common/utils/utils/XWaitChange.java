package xie.common.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class XWaitChange {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Object compareValue;

	private long timeout;

	private long beginTime;

	private boolean changedFlg;

	private boolean changedByValueFlg;
	private boolean changedByTimeFlg;

	public XWaitChange(Object compareValue) {
		init(compareValue, 0);
	}

	/**
	 * 
	 * @param compareValue
	 * @param timeout 毫秒
	 */
	public XWaitChange(Object compareValue, long timeout) {
		init(compareValue, timeout);
	}

	private void init(Object compareValue, long timeout) {
		this.compareValue = compareValue;
		this.timeout = timeout;
		beginTime = (new Date()).getTime();
		changedFlg = false;
		changedByValueFlg = false;
		changedByTimeFlg = false;
		logger.debug("beginTime: {}, compareValue: {}", beginTime, compareValue);
	}

	/**
	 * 重新设置比较对象，并且重置比较时间
	 */
	public synchronized void resetCompareValue(Object compareValue) {
		init(compareValue, timeout);
	}

	/**
	 * @return 经过的时间
	 */
	public long getPastTime() {
		return System.currentTimeMillis() - beginTime;
	}

	public synchronized boolean isChanged(Object value) {

		if (changedFlg) {
			return true;
		}

		doCompare(value);

		return changedFlg;
	}

	protected void doCompare(Object value) {
		if (compareValue.equals(value)) {
			if (timeout > 0) {
				// 设置超时的话， 如果超时，则认为已经改变了
				long nowTime = (new Date()).getTime();
				if (nowTime - beginTime > timeout) {
					changedFlg = true;
					changedByTimeFlg = true;
					logger.debug("isChanged timeout: {}, {}", compareValue, value);
				}
			}
		} else {
			changedFlg = true;
			changedByValueFlg = true;
			logger.debug("isChanged value: {}, {}", compareValue, value);
		}
	}

	/**
	 * 必须调用isChanged后，该方法才有效
	 */
	public boolean isChangedByValueFlg() {
		return changedByValueFlg;
	}

	/**
	 * 必须调用isChanged后，该方法才有效
	 */
	public boolean isChangedByTimeFlg() {
		return changedByTimeFlg;
	}
}
