package xie.common.utils.utils;

import java.util.HashMap;
import java.util.Map;

public class XFormatUtils {


	/**
	 * 将map的key和value格式化成Influxdb的字符串</br>
	 * map -> "key1"="value1","key2"="value2"...</br>
	 */
	public static String formatMap2InfluxdbLine(Map<String, Object> map) {
		return formatMap2Str(map, "\"", "'", "=", " AND ");
	}

	/**
	 * 将map的key和value格式化成字符串</br>
	 * map -> "key1"="value1","key2"="value2"...</br>
	 * 分号，等于号，逗号可以分别设定
	 */
	public static String formatMap2Str(Map<String, Object> map, String keySurround, String valueSurround, String keyValueSeparate, String entrySeparate) {
		if (map == null) {
			return null;
		}

		if (keySurround == null) {
			keySurround = "";
		}
		if (valueSurround == null) {
			valueSurround = "";
		}
		if (keyValueSeparate == null) {
			keyValueSeparate = "";
		}
		if (entrySeparate == null) {
			entrySeparate = "";
		}

		StringBuilder result = new StringBuilder("");
		boolean firstFlag = true;
		for (String key : map.keySet()) {
			String value = map.get(key) == null ? null : map.get(key).toString();
			if (firstFlag) {
				firstFlag = false;
			} else {
				result.append(entrySeparate);
			}
			result.append(keySurround).append(key).append(keySurround);
			result.append(keyValueSeparate);
			result.append(valueSurround).append(value).append(valueSurround);
		}

		return result.toString();
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		System.out.println(formatMap2InfluxdbLine(map));
		map.put("key1", "value1");
		System.out.println(formatMap2InfluxdbLine(map));
		map.put("key2", "value2");
		System.out.println(formatMap2InfluxdbLine(map));
		map.put("key3", "value3");
		System.out.println(formatMap2InfluxdbLine(map));
		map.put("key4", "value4");
		System.out.println(formatMap2InfluxdbLine(map));
	}
}
