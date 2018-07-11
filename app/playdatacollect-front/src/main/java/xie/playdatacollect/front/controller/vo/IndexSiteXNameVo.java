package xie.playdatacollect.front.controller.vo;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class IndexSiteXNameVo {

	/** 网站名 + 节目名 */
	private Map<String, Map<String, IndexPlayDataVo>> siteNameMap = new LinkedHashMap<>();
	/** 节目名 + 网站名 */
	private Map<String, Map<String, IndexPlayDataVo>> nameSiteMap = new LinkedHashMap<>();
	/** 节目名 + 网站名 */
	private Map<String, IndexPlayDataVo> keyMap = new LinkedHashMap<>();

	public void setKeyMap(Map<String, IndexPlayDataVo> map) {
		this.keyMap = map;
	}

	public void setValue(String site, String name, IndexPlayDataVo val) {
		this.keyMap.put(site + "_" + name, val);

		Map<String, IndexPlayDataVo> map = nameSiteMap.get(name);
		if (map == null) {
			map = new LinkedHashMap<>();
			nameSiteMap.put(name, map);
		}
		map.put(site, val);
	}

	public IndexPlayDataVo getSiteNameValue(String site, String name) {
		return keyMap.get(site + "_" + name);
	}

	public IndexPlayDataVo getValue(String site, String name) {
		return getSiteNameValue(site, name);
	}

	public IndexPlayDataVo getValue(String key) {
		return keyMap.get(key);
	}

	public Map<String, Map<String, IndexPlayDataVo>> getNameSiteMap() {
		return nameSiteMap;
	}

	public void initAllData() {
		nameSiteMap.forEach((name, siteMap) -> {
			IndexPlayDataVo allVo = new IndexPlayDataVo();
			allVo.setName(name);
			allVo.setSite("all");
			siteMap.forEach((site, vo) -> {
				mergeVo(allVo, vo, IndexPlayDataVo::getNowPlayCount, IndexPlayDataVo::getNowPlayTime, IndexPlayDataVo::setNowPlayCount, IndexPlayDataVo::setNowPlayTime);
				mergeVo(allVo, vo, IndexPlayDataVo::getPreHourPlayCount, IndexPlayDataVo::getPreHourPlayTime, IndexPlayDataVo::setPreHourPlayCount, IndexPlayDataVo::setPreHourPlayTime);
				mergeVo(allVo, vo, IndexPlayDataVo::getPreDayPlayCount, IndexPlayDataVo::getPreDayPlayTime, IndexPlayDataVo::setPreDayPlayCount, IndexPlayDataVo::setPreDayPlayTime);
				mergeVo(allVo, vo, IndexPlayDataVo::getPreWeekPlayCount, IndexPlayDataVo::getPreWeekPlayTime, IndexPlayDataVo::setPreWeekPlayCount, IndexPlayDataVo::setPreWeekPlayTime);
				mergeVo(allVo, vo, IndexPlayDataVo::getPreMonthPlayCount, IndexPlayDataVo::getPreMonthPlayTime, IndexPlayDataVo::setPreMonthPlayCount, IndexPlayDataVo::setPreMonthPlayTime);
				mergeVo(allVo, vo, IndexPlayDataVo::getPreYearPlayCount, IndexPlayDataVo::getPreYearPlayTime, IndexPlayDataVo::setPreYearPlayCount, IndexPlayDataVo::setPreYearPlayTime);
			});
			setValue("all", name, allVo);
		});
	}

	private void mergeVo(IndexPlayDataVo data, IndexPlayDataVo source, Function<IndexPlayDataVo, Long> getCount, Function<IndexPlayDataVo, Instant> getTime, BiConsumer<IndexPlayDataVo, Long> setCount, BiConsumer<IndexPlayDataVo, Instant> setTime) {
		// play data
		Long sourceCount = getCount.apply(source);
		Long dataCount = getCount.apply(data);
		if (sourceCount != null) {
			if (dataCount == null) {
				dataCount = sourceCount;
			} else {
				dataCount = dataCount + sourceCount;
			}
			setCount.accept(data, dataCount);
		}

		// time
		Instant sourceInstant = getTime.apply(source);
		Instant dataInstant = getTime.apply(data);
		if (sourceInstant != null) {
			if (dataInstant == null) {
				dataInstant = sourceInstant;
			} else {
				if (sourceInstant.compareTo(dataInstant) > 0) {
					dataInstant = sourceInstant;
				}
			}
			setTime.accept(data, dataInstant);
		}
	}
}
