package xie.playdatacollect.front.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.common.component.influxdb.action.XInfluxdbActionParameter;
import xie.common.spring.jpa.entity.EntityCache;
import xie.common.utils.constant.XConst;
import xie.common.utils.date.XDateUtil;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.influxdb.pojo.measurement.MHourPlayData;

import javax.annotation.Resource;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ControllerUtils {

	@Resource
	EntityCache entityCache;
	@Resource
	ProgramService programService;
	@Resource
	XInfluxdbAction xInfluxdbAction;

	/**
	 * @param model
	 * @param startDay
	 * @param groupByTimeSql 1h 1小时, 2h 2小时, 5h 5小时, 1d 1天, 1w 1周 ...
	 */
	public void searchPlayCountData(Model model, int startDay, String groupByTimeSql) {
		// 获取节目列表
		final List<ProgramEntity> programEntityList = getProgramEntityList();
		model.addAttribute("programEntityList", programEntityList);

		// 获取节目对应播放量数据
		List<MHourPlayData> playDataList = getPlayCountData(startDay, groupByTimeSql);


		Map<String, List<MHourPlayData>> playDataMap = getPlayDataMap(playDataList);

		model.addAttribute("playDataList", playDataList);
		model.addAttribute("playDataMap", playDataMap);
		Map<String, List<MHourPlayData>> sumPlayDataMap = getPlayDataSumMap(playDataList);

		// 总播放数
		model.addAttribute("sumPlayDataMap", sumPlayDataMap);

	}

	public List<ProgramEntity> getProgramEntityList() {
		return entityCache.get("ProgramEntityList",
					() -> {
						List<ProgramEntity> list = programService.findAll();
						Collator collator = Collator.getInstance(Locale.CHINESE);
						//		com.ibm.icu.text.Collator.getInstance(com.ibm.icu.util.ULocale.SIMPLIFIED_CHINESE);
						list.sort((o1, o2) -> collator.compare(o1.getFullName(), o2.getFullName()));
						return list;
					}
			);
	}

	public Map<String, List<MHourPlayData>> getPlayDataMap(int startDay, String groupByTimeSql) {
		return getPlayDataMap(getPlayCountData(startDay, groupByTimeSql));
	}

	/**
	 * 生成每个名字对应的数据
	 */
	public Map<String, List<MHourPlayData>> getPlayDataMap(List<MHourPlayData> playDataList) {
		return playDataList.stream().collect(
				Collectors.toMap(
						MHourPlayData::getName,
						Lists::newArrayList,
						(list1, list2) -> {
							list1.addAll(list2);
							return list1;
						},
						LinkedHashMap::new
				)
		);
	}


	/**
	 * 生成每个名字，网站对应的数据
	 */
	public Map<String, Map<String, List<MHourPlayData>>> getPlayDataSiteMap(Map<String, List<MHourPlayData>> playDataMap, List<String> siteList) {

		siteList.stream().collect(
				Collectors.toMap(
						String::toString,
						Maps::newHashMap,
						(),
						LinkedHashMap::new
				)
		)


		Map<String, Map<String, List<MHourPlayData>>> map = new HashMap<>();

		return null;
	}


	/**
	 * 生成每个名字对应的合计数据
	 */
	public Map<String, List<MHourPlayData>> getPlayDataSumMap(List<MHourPlayData> playDataList) {
		return playDataList.stream().collect(
				Collectors.toMap(
						MHourPlayData::getName,
						Lists::newArrayList,
						(List<MHourPlayData> list1, List<MHourPlayData> list2) -> {
							for (int i = 0; i < list1.size(); i++) {
								MHourPlayData m = list1.get(i);
								if (m.getTime().equals(list2.get(0).getTime())) {
									MHourPlayData newM = new MHourPlayData();
									BeanUtils.copyProperties(m, newM);

									Long playCount1 = m.getPlayCount() == null ? 0 : m.getPlayCount();
									Long playCount2 = list2.get(0).getPlayCount() == null ? 0 : list2.get(0).getPlayCount();
//									m.setPlayCount(playCount1);
//									list2.get(0).setPlayCount(playCount2);
									newM.setPlayCount(playCount1 + playCount2);
									list1.set(i, newM);
									return list1;
								}
							}

							list1.addAll(list2);
							return list1;
						},
						LinkedHashMap::new
				)
		);
	}

	public List<MHourPlayData> getPlayCountData(int startDay, String groupByTimeSql) {
		Date endDate = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE);
		Date startDate = DateUtils.addDays(endDate, -startDay);
		return entityCache.get("playDataList_" + groupByTimeSql + "_" + XDateUtil.convertToString(startDate, "yyyyMMdd"),
				() -> {
					XInfluxdbActionParameter parameter = new XInfluxdbActionParameter();
					parameter.setDatabase("play_data");
					parameter.setMeasurement("hour_base_data");
					parameter.setSelectSql(" SELECT MAX(\"播放数\") as \"播放数\" ");
//					parameter.putTag("名字", "LOST SONG 失落的歌谣");
					parameter.setStartDate(startDate);
					parameter.setEndDate(endDate);
					parameter.setGroupByTime(groupByTimeSql);
					parameter.addGroupByTagName("*");
					parameter.setFill(XInfluxdbActionParameter.FILL_LINEAR);
					List<MHourPlayData> list = xInfluxdbAction.queryDataResultToPojo(MHourPlayData.class, parameter);
					return list;
				}
				, XConst.SECOND_05_SEC * 1000);
	}

}
