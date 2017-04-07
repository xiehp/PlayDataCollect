package xie.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import xie.module.language.XELangLocal;

public class XEnum<T> implements Serializable {

	private String value;
	private String name;

	public XEnum() {
	}

	public XEnum(String value, String name) {
		this.value = value;
		this.name = name;

		putIntoMap(value, this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void putIntoMap(String value, XEnum<T> a) {
		Class<T> c = (Class<T>) a.getClass();
		Map<String, XEnum> valueMap = map.get(c);
		if (valueMap == null) {
			valueMap = new LinkedHashMap<>();
			map.put(c, valueMap);
		}

		valueMap.put(value.toLowerCase(), a);
	}

	// private final Map<String, XEnum<T>> valueMap = new LinkedHashMap<>();
	// private final Map<String, XEnum<T>> nameMap = new LinkedHashMap<>();
	private static final Map<Class<?>, Map<String, XEnum>> map = new LinkedHashMap<>();

	/**
	 * 获得标识ID
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获得文字说明
	 */
	public String getName() {
		return name;
	}

	public static <T extends XEnum> Map<String, XEnum> getValueMap(Class<T> c) {
		try {
			c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map.get(c);
	}

	public static <T extends XEnum> T parseValue(String value, Class<T> c) {
		if (value == null) {
			return null;
		}

		Map<String, XEnum> valueMap = getValueMap(c);
		if (valueMap == null) {
			return null;
		}
		return (T) valueMap.get(value.toLowerCase());
	}

	public static <T extends XEnum> T parseValue(String value) {
		// 方法1：通过SecurityManager的保护方法getClassContext()
		Class c = new SecurityManager() {
			public Class<?> getNowClass() {
				return getClassContext()[1].getClass();
			}
		}.getNowClass();
		System.out.println(c);

		String clazzName3 = new Object() {
			public String getClassName() {
				String clazzName = this.getClass().getName();
				return clazzName.substring(0, clazzName.lastIndexOf('$'));
			}
		}.getClassName();
		System.out.println(clazzName3);

		return (T) parseValue(value, c);
	}

}
