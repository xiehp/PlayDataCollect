package xie.playdatacollect.common.utils;

import xie.common.utils.string.XStringUtils;

public class PlayDataUtils {

	public static Long parseValue(Object value, Long defaultValue) {
		Long result = parseValue(value);
		if (result == null) {
			result = defaultValue;
		}

		return result;
	}

	/**
	 * 将字符串转换为数字，根据后缀中文符号做相应转换
	 */
	public static Integer parseIntegerValue(Object value) {
		Long result = parseValue(value);
		if (result == null) {
			return null;
		}
		return result.intValue();
	}

	/**
	 * 将字符串转换为数字，根据后缀中文符号做相应转换
	 *
	 * @param value
	 * @return
	 */
	public static Long parseValue(Object value) {
		long result = -1;

		if (value == null || value.toString() == null || "-".equals(value.toString())) {
			return result;
		}

		if (XStringUtils.isBlank(value.toString())) {
			return result;
		}

		String strValue = value.toString();
		strValue = strValue.replaceAll("[^0-9万亿AVav.]", "");

		if (strValue.contains("万")) {
			strValue = strValue.replace("万", "");
			result = (long) (Double.valueOf(strValue) * 10000);
		} else if (strValue.contains("亿")) {
			strValue = strValue.replace("亿", "");
			result = (long) (Double.valueOf(strValue) * 100000000);
		} else if (strValue.contains("AV") || strValue.contains("av")) {
			strValue = strValue.replace("AV", "");
			strValue = strValue.replace("av", "");
			result = Long.valueOf(strValue);
		} else {
			result = Long.valueOf(strValue);
		}

		return result;
	}

	public static Double parseDoubleValue(Object value, Double defaultValue) {
		Double result = parseDoubleValue(value);
		if (result == null) {
			result = defaultValue;
		}

		return result;
	}

	/**
	 * 将字符串转换为数字，根据后缀中文符号做相应转换
	 *
	 * @param value
	 * @return
	 */
	public static Double parseDoubleValue(Object value) {
		double result = -1;

		if (value == null || value.toString() == null || "-".equals(value.toString())) {
			return result;
		}

		if (XStringUtils.isBlank(value.toString())) {
			return result;
		}

		String strValue = value.toString();
		strValue = strValue.replaceAll("[^0-9万亿.]", "");

		if (strValue.contains("万")) {
			strValue = strValue.replace("万", "");
			result = (Double.valueOf(strValue) * 10000);
		} else if (strValue.contains("亿")) {
			strValue = strValue.replace("亿", "");
			result = (Double.valueOf(strValue) * 100000000);
		} else {
			result = Double.valueOf(strValue);
		}

		return result;
	}
}
