package xie.common.utils.utils;

import xie.common.utils.string.XStringUtils;

import java.util.Arrays;
import java.util.List;

public class XConvertUtils {

	private static final String[] BOOLEAN_TRUE = new String[]{"true", "yes", "1", "on", "ok"};
	private static final String[] BOOLEAN_FALSE = new String[]{"false", "no", "o", "off", "no"};

	public static boolean convert2Boolean(String val) {
		boolean result = false;
		// 除了checkArray以外所有情况都为默认值
		String[] checkArray = BOOLEAN_TRUE;
		for (String checkVal : checkArray) {
			if (checkVal.equalsIgnoreCase(val)) {
				result = true;
				break;
			}
		}

		return result;
	}

	public static boolean convert2Boolean(String val, boolean defaultValue) {
		try {
			if (val == null) {
				return defaultValue;
			}

			boolean result = convert2Boolean(val);
			return result;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String[] convert2Array(String val, String spiltStr) {
		if (val == null) {
			return new String[]{};
		}

		return val.split(spiltStr);
	}

	public static List<String> convert2List(String val, String spiltStr) {
		String[] array = convert2Array(val, spiltStr);

		return Arrays.asList(array);
	}


	public static Long convert2Long(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (XStringUtils.isBlank(value.toString())) {
			return defaultValue;
		}

		try {
			String strValue = value.toString();
			return Long.valueOf(strValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
