package xie.framework.core.service.dictionary.utils;

import xie.common.utils.utils.XConvertUtils;
import xie.framework.core.service.dictionary.common.PublicDictionaryConst;
import xie.framework.core.service.dictionary.entity.PublicDictionary;

import java.util.*;


public class PublicDictionaryLoader {

	/**
	 * 获取所有的字典数据
	 */
	@Deprecated
	public static Map<Integer, String> getIntMap(final String typeId) {
		final Map<String, String> tmpMap = DictionaryLoader.getInstance().getMap(typeId);
		final Map<Integer, String> newMap = new LinkedHashMap<>();
		for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
			newMap.put(Integer.valueOf(entry.getKey()), entry.getValue());
		}
		return newMap;
	}

	/**
	 * 获取所有的字典数据
	 */
	@Deprecated
	public static Map<Integer, PublicDictionary> getEntityIntMap(final String typeId) {
		final Map<String, PublicDictionary> tmpMap = DictionaryLoader.getInstance().getEntityMap(typeId);
		final Map<Integer, PublicDictionary> newMap = new LinkedHashMap<>();
		for (Map.Entry<String, PublicDictionary> entry : tmpMap.entrySet()) {
			newMap.put(Integer.valueOf(entry.getKey()), entry.getValue());
		}
		return newMap;
	}

	public static Map<Integer, String> getValidIntMap(final String typeId) {
		final Map<String, String> tmpMap = DictionaryLoader.getInstance().geValidtMap(typeId);
		final Map<Integer, String> newMap = new LinkedHashMap<>();
		for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
			newMap.put(Integer.valueOf(entry.getKey()), entry.getValue());
		}
		return newMap;
	}

	public static Map<Integer, PublicDictionary> getValidEntityIntMap(final String typeId) {
		final Map<String, PublicDictionary> tmpMap = DictionaryLoader.getInstance().getValidMapEntity(typeId);
		final Map<Integer, PublicDictionary> newMap = new LinkedHashMap<>();
		for (Map.Entry<String, PublicDictionary> entry : tmpMap.entrySet()) {
			newMap.put(Integer.valueOf(entry.getKey()), entry.getValue());
		}
		return newMap;
	}

	/**
	 * 获取所有的字典数据
	 */
	@Deprecated
	public static Map<String, String> getMap(final String typeId) {
		return DictionaryLoader.getInstance().getMap(typeId);
	}

	/**
	 * 获取所有的字典数据
	 */
	@Deprecated
	public static Map<String, PublicDictionary> getEntityMap(final String typeId) {
		return DictionaryLoader.getInstance().getEntityMap(typeId);
	}

	public static Map<String, String> getValidMap(final String typeId) {
		return DictionaryLoader.getInstance().geValidtMap(typeId);
	}

	public static Map<String, PublicDictionary> getValidEntityMap(final String typeId) {
		return DictionaryLoader.getInstance().getValidMapEntity(typeId);
	}

	public static String getValue(final String typeId, final String code) {
		Map<String, String> map = PublicDictionaryLoader.getValidMap(typeId);
		return map.get(code);
	}

	public static String getValue(final String typeId, final Integer code) {
		Map<Integer, String> map = PublicDictionaryLoader.getValidIntMap(typeId);
		return map.get(code);
	}

	public static String getSystemValue(final String code) {
		return getValue(PublicDictionaryConst.TYPE_SYSTEM, code);
	}

	public static boolean getSystemBooleanValue(final String code) {
		String value = getValue(PublicDictionaryConst.TYPE_SYSTEM, code);
		return XConvertUtils.convert2Boolean(value, false);
	}

	public static String[] getSystemArrayValue(final String code) {
		String value = getValue(PublicDictionaryConst.TYPE_SYSTEM, code);
		return XConvertUtils.convert2Array(value, ",");
	}

	public static List<String> getSystemListValue(final String code) {
		String value = getValue(PublicDictionaryConst.TYPE_SYSTEM, code);
		return XConvertUtils.convert2List(value, ",");
	}

	public static void reload() {
		DictionaryLoader.reload();
	}


	/**
	 * 以列表数组的形式获取配置文件的对应参数
	 */
	public static List<String> getPropertyList(String typeId, String codeId) {
		String property = getValue(typeId, codeId);
		return Arrays.asList(property.split(","));
	}


	//根据Value取Key
	public static String getKeyByValue(Map map, Object value) {
		String keys = "";
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object obj = entry.getValue();
			if (obj != null && obj.equals(value)) {
				keys = (String) entry.getKey();
			}


		}
		return keys;
	}
}
