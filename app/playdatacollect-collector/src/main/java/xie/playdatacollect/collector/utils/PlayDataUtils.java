package xie.playdatacollect.collector.utils;

import xie.common.string.XStringUtils;

public class PlayDataUtils {
	public static int parseValue(Object value) {
		int result = 0;

		if (value == null || value.toString() == null || "-".equals(value.toString())) {
			return result;
		}

		if (XStringUtils.isBlank(value.toString())) {
			return result;
		}

		if (value.toString().contains("万")) {
			value = value.toString().replace("万", "");
			result = (int) (Double.valueOf(value.toString()) * 10000);
		} else if (value.toString().contains("亿")) {
			value = value.toString().replace("亿", "");
			result = (int) (Double.valueOf(value.toString()) * 100000000);
		} else if (value.toString().contains("AV") || value.toString().contains("av")) {
			value = value.toString().replace("AV", "");
			value = value.toString().replace("av", "");
			result = Integer.valueOf(value.toString());
		} else {
			result = Integer.valueOf(value.toString());
		}

		return result;
	}
}
