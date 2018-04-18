package xie.framework.core.service.dictionary.utils;

import org.slf4j.Logger;
import xie.common.spring.utils.SpringUtils;
import xie.common.utils.constant.XConst;
import xie.common.utils.log.XLog;
import xie.framework.core.service.dictionary.entity.PublicDictionary;
import xie.framework.core.service.dictionary.service.PublicDictionaryService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DictionaryLoader {

	private static final Logger _log = XLog.getLog(DictionaryLoader.class);

	protected Map<String, Map<String, String>> typeMap = new LinkedHashMap<>();
	protected Map<String, Map<String, PublicDictionary>> typeEntityMap = new LinkedHashMap<>();

	public static DictionaryLoader instance;

	public Map<String, Map<String, String>> getTypeMap() {
		return typeMap;
	}

	public Map<String, Map<String, PublicDictionary>> getTypeEntityMap() {
		return typeEntityMap;
	}

	public DictionaryLoader() {
		final PublicDictionaryService publicDictionaryService = SpringUtils.getBean(PublicDictionaryService.class);
		final Map<String, List<PublicDictionary>> dictionaryMap = publicDictionaryService.getAllDictionaryMap();

		for (final String typeId : dictionaryMap.keySet()) {
			List<PublicDictionary> PublicDictionaryList = dictionaryMap.get(typeId);

			Map<String, String> map = new LinkedHashMap<>();
			Map<String, PublicDictionary> vomap = new LinkedHashMap<>();

			typeMap.put(typeId, map);
			typeEntityMap.put(typeId, vomap);

			for (final PublicDictionary publicDictionary : PublicDictionaryList) {
				map.put(publicDictionary.getCode(), publicDictionary.getValue());
				vomap.put(publicDictionary.getCode(), publicDictionary);
			}
		}
	}

	public static DictionaryLoader getInstance() {
		if (instance == null) {
			synchronized (DictionaryLoader.class) {
				if (instance == null) {
					instance = new DictionaryLoader();
				}
			}
		}
		return instance;
	}

	public static void reload() {
		final PublicDictionaryService dictionaryBO = SpringUtils.getBean(PublicDictionaryService.class);
		_log.info("start load dictionary cache ........");
		dictionaryBO.reloadCache();
		instance = null;
		getInstance();
		_log.info("finish load dictionary cache");
	}

	public static void reload(final String typeId) {
		final PublicDictionaryService dictionaryBO = SpringUtils.getBean(PublicDictionaryService.class);
		_log.info("start load dictionary " + typeId + " cache ........");
		final List<PublicDictionary> dictionaryList = dictionaryBO.findByTypeId(typeId);

		final Map<String, String> tempmap = new LinkedHashMap<>();
		final Map<String, PublicDictionary> tempvomap = new LinkedHashMap<>();
		for (final PublicDictionary publicDictionary : dictionaryList) {
			tempmap.put(publicDictionary.getCode(), publicDictionary.getValue());
			tempvomap.put(publicDictionary.getCode(), publicDictionary);
		}

		final Map<String, String> strMap = DictionaryLoader.getInstance().getMap(typeId);
		final Map<String, PublicDictionary> voMap = DictionaryLoader.getInstance().getEntityMap(typeId);

		if (strMap == null) {
			DictionaryLoader.getInstance().getTypeMap().put(typeId, tempmap);
		} else {
			strMap.clear();
			strMap.putAll(tempmap);
		}

		if (voMap == null) {
			DictionaryLoader.getInstance().getTypeEntityMap().put(typeId, tempvomap);
		} else {
			voMap.clear();
			voMap.putAll(tempvomap);
		}

		_log.info("finish load dictionary " + typeId + " cache");
	}

	/**
	 * 获取所有的字典数据
	 */
	@Deprecated
	public Map<String, String> getMap(final String typeId) {
		return typeMap.get(typeId);
	}

	/**
	 * 获取所有的字典数据
	 */
	@Deprecated
	public Map<String, PublicDictionary> getEntityMap(final String typeId) {
		Map<String, PublicDictionary> map = typeEntityMap.get(typeId);
		if (map == null) {
			map = new LinkedHashMap<>();
			typeEntityMap.put(typeId, map);
		}
		return map;
	}

	/**
	 * 获取可用的字典数据
	 */
	public Map<String, String> geValidtMap(final String typeId) {
		final Map<String, PublicDictionary> vomap = getValidMapEntity(typeId);
		final Map<String, String> map = new LinkedHashMap<>();
		for (final String code : vomap.keySet()) {
			final PublicDictionary entity = vomap.get(code);
			map.put(code, entity.getValue());
		}
		return map;
	}

	/**
	 * 获取可用的字典数据
	 */
	public Map<String, PublicDictionary> getValidMapEntity(final String typeId) {
		final Map<String, PublicDictionary> voMap = getEntityMap(typeId);
		final Map<String, PublicDictionary> map = new LinkedHashMap<>();
		for (final String code : voMap.keySet()) {
			final PublicDictionary entity = voMap.get(code);
			if (!XConst.FLAG_INTEGER_YES.equals(entity.getDeleteFlag())) {
				map.put(code, entity);
			}
		}
		return map;
	}

}
