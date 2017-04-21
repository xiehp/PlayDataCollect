package xie.base;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 现在的问题：<br>
 * 1:所有子类现在必须继承无参构造函数，以解决类没有初期化导致VALUE_TO_EUMN_MAP中没有数据<br>
 * 2:所有子类需要自己实现parseValue方法<br>
 *
 * @param <E>
 */
public class XEnum<E extends XEnum<E>> implements Serializable {

	private static final long serialVersionUID = 7730321563555103221L;

	private String value;
	private String name;

	public XEnum(String value, String name) {

		this.value = value;
		this.name = name;

		if (value != null && name != null) {
			putIntoMap(value, this);
		}
	}

	public void putIntoMap(String value, XEnum<E> a) {
		Class<?> c = (Class<?>) a.getClass();
		Map<String, XEnum<?>> valueMap = VALUE_TO_EUMN_MAP.get(c);
		if (valueMap == null) {
			valueMap = new LinkedHashMap<>();
			VALUE_TO_EUMN_MAP.put(c, valueMap);
		}

		valueMap.put(value.toLowerCase(), a);
	}

	// private final Map<String, XEnum<T>> valueMap = new LinkedHashMap<>();
	// private final Map<String, XEnum<T>> nameMap = new LinkedHashMap<>();
	/** 用于存放所有枚举的值和类的对应关系，value -> XENUM枚举实例 */
	private static final Map<Class<?>, Map<String, XEnum<?>>> VALUE_TO_EUMN_MAP = new LinkedHashMap<>();

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

	/**
	 * 用于存放所有枚举的值和类的对应关系，value -> XENUM枚举实例
	 * 
	 * @param c
	 * @return
	 */
	public static <T extends XEnum<?>> Map<String, XEnum<?>> getValueMap(Class<T> c) {
		Map<String, XEnum<?>> map = VALUE_TO_EUMN_MAP.get(c);
		if (map != null) {
			return map;
		}

		try {
			// 防止由于没有加载导致的数据不存在问题
			// c.newInstance();

			Constructor<?>[] constructors = c.getConstructors();
			if (constructors.length > 0) {
				constructors[0].newInstance();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		map = VALUE_TO_EUMN_MAP.get(c);
		if (map == null) {
			map = new LinkedHashMap<>();
			VALUE_TO_EUMN_MAP.put(c, map);
		}

		return VALUE_TO_EUMN_MAP.get(c);
	}

	public static <T extends XEnum<T>> T parseValue(String value, Class<T> c) {
		if (value == null) {
			return null;
		}

		Map<String, XEnum<?>> valueMap = getValueMap(c);
		if (valueMap == null) {
			return null;
		}

		return (T) valueMap.get(value.toLowerCase());
	}

	private static <T extends XEnum<T>> T parseValue(String value) {
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

	@Override
	public String toString() {
		return super.toString() + ", " + getValue() + ", " + getName();
	}

	public static <T extends XEnum<T>> List<T> values(Class<T> c) {
		List<T> list = new ArrayList<>();
		getValueMap(c).values().forEach(e -> {
			list.add((T) e);
		});
		return list;
	}
}
