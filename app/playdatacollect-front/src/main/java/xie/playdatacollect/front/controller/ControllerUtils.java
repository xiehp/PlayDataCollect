package xie.playdatacollect.front.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
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
import xie.playdatacollect.front.controller.vo.IndexPlayDayaVo;
import xie.playdatacollect.front.controller.vo.IndexSiteXNameVo;
import xie.playdatacollect.influxdb.pojo.measurement.MHourPlayData;

import javax.annotation.Resource;
import java.text.Collator;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.BiConsumer;
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
	public IndexSiteXNameVo getPlayDataSiteMap() {

		// 获取时，天，周年的时间分组数据
		List<MHourPlayData> hourPlayDataList = getPlayCountData(1, "1h");
		List<MHourPlayData> dayPlayDataList = getPlayCountData(2, "1d");
		List<MHourPlayData> weekPlayDataList = getPlayCountData(15, "1w");
		List<MHourPlayData> monthPlayDataList = getPlayCountData(63, "30d");
//
//		// 以名字命名的时，天，周年的时间分组数据
//		Map<String, List<MHourPlayData>> hourPlayDataMap = getPlayDataMap(hourPlayDataList);
//		Map<String, List<MHourPlayData>> dayPlayDataMap = getPlayDataMap(dayPlayDataList);
//		Map<String, List<MHourPlayData>> weekPlayDataMap = getPlayDataMap(weekPlayDataList);
//		Map<String, List<MHourPlayData>> monthPlayDataMap = getPlayDataMap(monthPlayDataList);
//
//		// 获取时，天，周年的时间分组的整合数据
//		Map<String, List<MHourPlayData>> hourPlayDataSumMap = getPlayDataSumMap(hourPlayDataList);
//		Map<String, List<MHourPlayData>> dayPlayDataSumMap = getPlayDataSumMap(dayPlayDataList);
//		Map<String, List<MHourPlayData>> weekPlayDataSumMap = getPlayDataSumMap(weekPlayDataList);
//		Map<String, List<MHourPlayData>> monthPlayDataSumMap = getPlayDataSumMap(monthPlayDataList);


		IndexSiteXNameVo indexSiteXNameVo = new IndexSiteXNameVo();

		// 整合成名字，网站整合数据
		// 当前时间
		Instant nowHourInstant = Instant.now().truncatedTo(ChronoUnit.HOURS);
		forPlayData(indexSiteXNameVo, hourPlayDataList, (vo, playData) -> {
			if (playData.getPlayCount() != null && playData.getTime().compareTo(nowHourInstant) >= 0 && playData.getPlayCount() > 0) {
				vo.setNowPlayCount(playData.getPlayCount());
				vo.setNowPlayTime(playData.getTime());
			}
		});
		// 前一个小时
		forPlayData(indexSiteXNameVo, hourPlayDataList, (vo, playData) -> {
			if (playData.getTime().compareTo(nowHourInstant) < 0) {
				if (vo.getPreHourPlayTime() == null || playData.getTime().compareTo(vo.getPreHourPlayTime()) > 0) {
					vo.setPreHourPlayCount(playData.getPlayCount());
					vo.setPreHourPlayTime(playData.getTime());
				}
			}
		});
		// 前一天
		Instant nowDayInstant = XDateUtil.truncateDay(Instant.now());
		forPlayData(indexSiteXNameVo, dayPlayDataList, (vo, playData) -> {
			if (playData.getTime().compareTo(nowDayInstant) < 0) {
				if (vo.getPreDayPlayTime() == null || playData.getTime().compareTo(vo.getPreDayPlayTime()) > 0) {
					vo.setPreDayPlayCount(playData.getPlayCount());
					vo.setPreDayPlayTime(playData.getTime());
				}
			}
		});
		// 前一周
		Instant nowWeekInstant = XDateUtil.truncateWeek(Instant.now());
		forPlayData(indexSiteXNameVo, weekPlayDataList, (vo, playData) -> {
			if (playData.getTime().compareTo(nowWeekInstant) < 0) {
				if (vo.getPreWeekPlayTime() == null || playData.getTime().compareTo(vo.getPreWeekPlayTime()) > 0) {
					vo.setPreWeekPlayCount(playData.getPlayCount());
					vo.setPreWeekPlayTime(playData.getTime());
				}
			}
		});
		// 前一月
		Instant nowMonthInstant = XDateUtil.truncateMonth(Instant.now());
		forPlayData(indexSiteXNameVo, monthPlayDataList, (vo, playData) -> {
			if (playData.getTime().compareTo(nowMonthInstant) < 0) {
				if (vo.getPreMonthPlayTime() == null || playData.getTime().compareTo(vo.getPreMonthPlayTime()) > 0) {
					vo.setPreMonthPlayCount(playData.getPlayCount());
					vo.setPreMonthPlayTime(playData.getTime());
				}
			}
		});


		return indexSiteXNameVo;
	}

	private void forPlayData(IndexSiteXNameVo indexSiteXNameVo, List<MHourPlayData> playDataList, BiConsumer<IndexPlayDayaVo, MHourPlayData> con) {

		// 整合成名字，网站整合数据
		playDataList.forEach((playData) -> {
			IndexPlayDayaVo vo = indexSiteXNameVo.getValue(playData.getSite(), playData.getName());
			if (vo == null) {
				vo = new IndexPlayDayaVo();
				indexSiteXNameVo.setValue(playData.getSite(), playData.getName(), vo);
			}

			con.accept(vo, playData);
		});
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
//							 		m.setPlayCount(playCount1);
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
