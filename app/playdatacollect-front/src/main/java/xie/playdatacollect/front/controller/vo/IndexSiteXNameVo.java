package xie.playdatacollect.front.controller.vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class IndexSiteXNameVo {

	/** 网站名 + 节目名 */
	private Map<String, Map<String, IndexPlayDayaVo>> siteNameMap = new LinkedHashMap<>();
	/** 节目名 + 网站名 */
	private Map<String, Map<String, IndexPlayDayaVo>> nameSiteMap = new LinkedHashMap<>();
	/** 节目名 + 网站名 */
	private Map<String, IndexPlayDayaVo> keyMap = new LinkedHashMap<>();

	public void setKeyMap(Map<String, IndexPlayDayaVo> map) {
		this.keyMap = map;
	}

	public void setValue(String site, String name, IndexPlayDayaVo val) {
		this.keyMap.put(site + "_" + name, val);
	}

	public IndexPlayDayaVo getSiteNameValue(String site, String name) {
		return keyMap.get(site + "_" + name);
	}

	public IndexPlayDayaVo getValue(String site, String name) {
		return getSiteNameValue(site, name);
	}

	public IndexPlayDayaVo getValue(String key) {
		return keyMap.get(key);
	}


}
