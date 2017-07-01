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
	public static final String YMD_FULL3 = "yyyyMMdd HHmmss";

	public static void main(String[] args) throws ParseException {
		;
		System.out.println(formatTime(XTimeUtils.parseFromTimeStr("0:04:13.31", "H:mm:ss.SS"), 2));
		System.out.println(formatTime(XTimeUtils.parseFromTimeStr("0:04:13.31", "H:mm:ss.SSS"), 2));
		System.out.println(formatTime(XTimeUtils.parseFromTimeStr("0:04:13.031", "H:mm:ss.SS"), 2));
		System.out.println(formatTime(XTimeUtils.parseFromTimeStr("0:04:13.310", "H:mm:ss.SS"), 2));
		System.out.println(formatTime(11L, 2));
		System.out.println(formatTime(111L, 2));
		System.out.println(formatTime(1111L, 2));
		System.out.println(formatTime(5000L, 3));
		System.out.println(formatTime(555000L, 3));
		System.out.println(formatTime(555001L, 3));
		System.out.println(formatTime(555010L, 3));
		System.out.println(formatTime(555100L, 3));

		System.out.println(seekDate(new Date(), 111));
		;
		System.out.println(seekDate(new Date(), 333));
		;
		System.out.println(seekDate(new Date(), -111));
		;
		System.out.println(seekDate(new Date(), -333));
		;
	}

	/**
	 * 
	 * @param time 一个以0开始的时间，精确到微秒
	 * @param style 0: "mm:ss" 1: "SSS" 2: "mm:ss:SSS" 3: "mm分ss秒SSS", SSS为0时省略
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
			return String.format("%02d:%02d.%03d", minutes, seconds, micro);
		} else if (style == 3) {
			String result = "";
			if (micro == 0) {
				result = String.format("%02d分%02d秒", minutes, seconds);
			} else {
				result = String.format("%02d分%02d秒%03d", minutes, seconds, micro);
			}
			return result;
		} else {
			return String.format("%02d:%02d", minutes, seconds);
		}
	}

	/**
	 * 
	 * @param time 一个以0开始的时间，精确到微秒
	 * @param pattern
	 * @return
	 */
	public static String formatTime(Long time, String pattern) {
		Date date = new Date(time);
		return convertToString(date, pattern);
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
		return new Date(date.getTime() + (long) dayNum * 86400000L);
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
		if (date == null) {
			return null;
		}

		if (format == null) {
			format = YMD_FULL;
		}

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

	public static final String[] PATTERNS_ALL = new String[] {
			"yyyy-MM-dd HH:mm:ss",
			"yyyy/MM/dd HH:mm:ss",
			"yyyy年MM月dd HH:mm:ss",
			"yyyy-MM-dd",
			"yyyy/MM/dd",
			"yyyy年MM年dd",
			"yyyy-MM",
			"yyyy/MM",
			"yyyy年MM",
			"yyyyMM"
	};

	/**
	 * 使用多种方式转换
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date fromString(String date) throws ParseException {
		if (date == null) {
			return null;
		}

		for (String pattern : PATTERNS_ALL) {
			if (date.length() == pattern.length()) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);
					return sdf.parse(date);
				} catch (Exception e) {
				}
			}
		}

		throw new ParseException("[" + date + "]无法转换成日期", 0);
	}
}
