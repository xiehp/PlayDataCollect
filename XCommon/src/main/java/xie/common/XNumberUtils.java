package xie.common;


public class XNumberUtils {

	public static String getStringValue(Object object) {
		if (object == null) {
			return null;
		}
		return String.valueOf(object);
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
			return Integer.valueOf((String) object);
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
			return Double.valueOf((String) object);
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
			return Long.valueOf((String) object);
		} else {
			throw new RuntimeException("data is not Integer or Double or String.");
		}
	}

}
