/**
 * @since 2014-11-30 上午9:31:12
 */
package xie.common.stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @since 2014-11-30 上午9:31:12
 */
public class XNumberListener {
	volatile long allNumber = 0;
	volatile long nowNumber = 0;

	public XNumberListener(long allNumber) {
		setAllNumber(allNumber);
	}

	public synchronized void setAllNumber(long allNumber) {
		this.allNumber = allNumber;
	}

	public synchronized void setNumber(long number) {
		this.nowNumber = number;
	}

	public synchronized void addNumber(long number) {
		this.nowNumber += number;
	}

	public long getNumber() {
		return this.nowNumber;
	}

	public long getAllNumber() {
		return this.allNumber;
	}

	public double getPercent() {
		if (allNumber == 0) {
			return 0;
		}
		return nowNumber / (double) allNumber;
	}

	/**
	 * 99.9%
	 * 
	 * @since 2014-11-30 上午9:49:35
	 */
	public String getPercentString() {
		double percent = getPercent();
		NumberFormat numberFormat = new DecimalFormat("#,##0.0");
		return numberFormat.format(percent);
	}

	/**
	 * XX数量/XX数量
	 * 
	 * @param unitStr
	 * @return
	 * @since 2014-11-30 上午9:55:40
	 */
	public String getProcessString(String unitStr) {
		if (unitStr == null) {
			unitStr = "";
		}

		return nowNumber + unitStr + "/" + allNumber + unitStr;
	}
}
