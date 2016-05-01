package xie.common.date;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String YMD1 = "yyyy-MM-dd";
	public static final String YMD2 = "dd/MM/yy";
	public static final String YMD3 = "yyyyMMdd";
	public static final String YMD4 = "ddMMyyyy";
	public static final String YMD5 = "yyyy-MM";
	public static final String YMD6 = "HH";
	public static final String YMD7 = "yyyy/MM/dd";
	public static final String YMD_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD_FULL2 = "yyyyMMddHHmmssSSS";

	/**
	 * 
	 * @param time 一个以0开始的时间，精确到微秒
	 * @param style 0: "mm:ss" 1: "SSS" 2: "mm:ss:SSS"
	 * @return
	 */
	public static String formatTime(Long time, int style) {
		if (time == null) {
			time = 0L;
		}
		int micro = (int) (time % 1000);
		int remainder = (int) (time / 1000);
		// int hours = (int) (time / 3600);
		// int remainder = (int) (time - hours * 3600);
		int minutes = remainder / 60;
		remainder = remainder - minutes * 60;
		int seconds = remainder;
		// return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, mili);
		if (style == 1) {
			return String.format("%03d", micro);
		} else if (style == 2) {
			return String.format("%02d:%02d:%03d", minutes, seconds, micro);
		} else {
			return String.format("%02d:%02d", minutes, seconds);
		}
	}

	/**
	 * Convert date type from String to Date
	 * 
	 * @param temp Sample:2005-10-12
	 * @return Date
	 */
	public static String convertToString(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String convertToString(Date date) {
		return convertToString(date, YMD_FULL);
	}

	public static Date seekDate(Date date, int dayNum) {
		return new Date(date.getTime() + (long) (dayNum * 86400000));
	}

	public static Date trimDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);

		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);

		return rightNow.getTime();
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static Long getCurrentTime() {
		return new Date().getTime();
	}

	public static int getAge(String birth, String format) {
		if (birth == null) {
			return 0;
		}
		try {
			return getAge(convertFromString(birth, format));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static int getAge(Date birth) {
		if (birth == null) {
			return 0;
		}
		Calendar rightNow = Calendar.getInstance();
		int nowYear = rightNow.get(Calendar.YEAR);
		rightNow.setTime(birth);
		int birthYear = rightNow.get(Calendar.YEAR);

		return nowYear - birthYear;

	}

	public static DateFormat getCnDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public static Date convertFromString(String date, String format) throws ParseException {
		if (date == null)
			return null;
		if (format == null)
			format = YMD_FULL;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	public static Date convertFromString(String date) throws ParseException {
		return convertFromString(date, null);
	}

	public static Date convertDate(String date) throws ParseException {
		return convertFromString(date, YMD1);
	}

	public static Date convertDateMutip(String date) throws ParseException {
		if (null == date)
			return null;
		if (date.length() > 10) {
			return convertFromString(date);
		} else {
			return convertDate(date);
		}
	}

	public static double normalMultiply(Double value1, Double value2) throws Exception {
		BigDecimal b1 = new BigDecimal(value1.toString());
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.multiply(b2).doubleValue();
	}

	public static double normalAdd(Double value1, Double value2) throws Exception {
		BigDecimal b1 = new BigDecimal(value1.toString());
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.add(b2).doubleValue();
	}

}
