package xie.common.utils.utils;

import java.util.Arrays;
import java.util.List;

public class XConvertUtils {

	private static final String[] BOOLEAN_TRUE = new String[]{"true", "yes", "1", "on"};
	private static final String[] BOOLEAN_FALSE = new String[]{"false", "no", "o", "off"};

	public static boolean convert2Boolean(String val, boolean defaultValue) {
		if (val == null) {
			return defaultValue;
		}

		// 除了checkArray以外所有情况都为默认值
		String[] checkArray = BOOLEAN_TRUE;
		if (defaultValue) {
			checkArray = BOOLEAN_FALSE;
		}

		boolean result = defaultValue;
		for (String checkVal : checkArray) {
			if (checkVal.equalsIgnoreCase(val)) {
				result = !defaultValue;
				break;
			}
		}

		return result;
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
}
