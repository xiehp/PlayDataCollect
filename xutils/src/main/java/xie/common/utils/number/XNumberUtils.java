package xie.common.utils.number;

import xie.common.utils.string.XStringUtils;

import java.math.BigDecimal;

public class XNumberUtils {

	public static int add(Integer a, Integer b) {
		if (a == null) {
			a = 0;
		}
		if (b == null) {
			b = 0;
		}

		return a + b;
	}

	public static long add(Long a, Long b) {
		if (a == null) {
			a = 0L;
		}
		if (b == null) {
			b = 0L;
		}

		return a + b;
	}

	public static String getStringValue(Object object) {
		if (object == null) {
			return null;
		}
		return String.valueOf(object);
	}

	public static long[] split(String str) {
		if (XStringUtils.isBlank(str)) {
			return new long[0];
		}

		String[] strs = str.split(",");

		long[] longArray = new long[strs.length];
		for (int i = 0; i < strs.length; i++) {
			String longStr = strs[i];
			longArray[i] = getLongValue(longStr);
		}

		return longArray;
	}

	public static Integer getIntegerValue(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Integer) {
			return (Integer) object;
		} else if (object instanceof Double) {
			return ((Double) object).intValue();
		} else if (object instanceof String) {
			return Integer.valueOf(((String) object).trim());
		} else {
			throw new RuntimeException("data is not Integer or Double or String.");
		}
	}

	public static Double getDoubleValue(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Integer) {
			return ((Integer) object).doubleValue();
		} else if (object instanceof Double) {
			return (Double) object;
		} else if (object instanceof String) {
			return Double.valueOf(((String) object).trim());
		} else {
			throw new RuntimeException("data is not Integer or Double or String.");
		}
	}

	public static Long getLongValue(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Integer) {
			return ((Integer) object).longValue();
		} else if (object instanceof Double) {
			return ((Double) object).longValue();
		} else if (object instanceof String) {
			return Long.parseLong(((String) object).trim());
		} else {
			throw new RuntimeException("data is not Integer or Double or String.");
		}
	}

	public static BigDecimal getBigDecimal(String str) {
		if (str == null || "".equals(str.trim())) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(str);
	}

}
