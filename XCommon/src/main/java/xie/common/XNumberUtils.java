package xie.common;

import xie.common.string.XStringUtils;

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

	public static int getIntegerValue(Object object) {
		if (object == null) {
			throw new RuntimeException("FAmf.getIntegerValue with null param.");
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

	public static double getDoubleValue(Object object) {
		if (object == null) {
			throw new RuntimeException("FAmf.getDoubleValue with null param.");
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

	public static long getLongValue(Object object) {
		if (object == null) {
			throw new RuntimeException("FAmf.getLongValue with null param.");
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

}
