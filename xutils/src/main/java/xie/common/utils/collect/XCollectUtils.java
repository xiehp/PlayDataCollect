package xie.common.utils.collect;

import xie.common.utils.string.XStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XCollectUtils {

	public static void removeNull(Map<?, ?> map) {
		if (map == null) {
			return;
		}

		List<Object> removeList = new ArrayList<>();
		for (Object key : map.keySet()) {
			if (map.get(key) == null) {
				removeList.add(key);
			}
		}

		for (Object key : removeList) {
			map.remove(key);
		}
	}

	public static void removeKey(Map<String, Object> map, List<String> removeKeyList, boolean ignoreCaseFlag) {
		if (map == null) {
			return;
		}

		if (removeKeyList == null || removeKeyList.isEmpty()) {
			return;
		}

		List<String> removeExistKeyList = new ArrayList<>();
		for (String key : map.keySet()) {
			if (ignoreCaseFlag) {
				if (XStringUtils.existIgnoreCase(removeKeyList, key)) {
					removeExistKeyList.add(key);
				}
			} else {
				if (removeKeyList.contains(key)) {
					removeExistKeyList.add(key);
				}
			}
		}

		for (String key : removeExistKeyList) {
			map.remove(key);
		}
	}
}
