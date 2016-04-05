package xie.common.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public void setCompareValue(Object compareValue) {
		init(compareValue, timeout);
	}

	public boolean isChanged() {
		return changedFlg;
	}

	public boolean isChangedByValueFlg() {
		return changedByValueFlg;
	}

	public boolean isChangedByTimeFlg() {
		return changedByTimeFlg;
	}

	/**
	 * @return 经过的时间
	 */
	public long getPastTime() {
		return new Date().getTime() - beginTime;
	}

	public boolean isChanged(Object value) {

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
}
