package xie.playdatacollect.core.utils;

import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xie.common.spring.jpa.entity.BaseEntity;
import xie.common.utils.date.DateUtil;
import xie.common.utils.log.XLog;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.entity.url.SourcesEntity;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * 用于初始化一些基础数据
 */
@Component
@Order(0)
//public class InitEntityDataRunner implements ApplicationRunner {
public class InitEntityDataRunner {

	Logger log = XLog.getLog(InitEntityDataRunner.class);

	@Resource
	AllDaoUtil allDaoUtil;

	private boolean batchFlag = false;
	private Map<BaseDao, List<BaseEntity>> batchDataMap = new LinkedHashMap<>();

	private void beginBatch() {
		log.info("beginBatch");
		batchFlag = true;
	}

	private void putBatchData(BaseDao dao, BaseEntity entity) {
		List<BaseEntity> list;
		if (batchDataMap.containsKey(dao)) {
			list = batchDataMap.get(dao);
		} else {
			list = new ArrayList<>();
			batchDataMap.put(dao, list);
		}

		list.add(entity);
	}

	private void updateBatch() {
		log.info("begin updateBatch, batchData dao size:{}", batchDataMap.size());

		if (batchDataMap.size() > 0) {
			for (BaseDao dao : batchDataMap.keySet()) {
				List<BaseEntity> list = batchDataMap.get(dao);
				log.info("update {}, size:{}", dao.getClass().getInterfaces()[0].getSimpleName(), list.size());
				dao.saveAll(list);
			}
		}

		batchDataMap.clear();
		batchFlag = false;
		log.info("end updateBatch");
	}

	//@Override
	public void run(ApplicationArguments args) throws ParseException {
		log.info("InitEntityDataRunner start");

		beginBatch();

		// 网站信息
		saveSourcesData(PlayDataConst.SOURCE_KEY_BILIBILI, "哔哩哔哩弹幕网", "哔哩哔哩", "BL", "https://www.bilibili.com/");
		saveSourcesData(PlayDataConst.SOURCE_KEY_IQIYI, "爱奇艺网", "爱奇艺", "IQY", "https://www.iqiyi.com/");
		saveSourcesData(PlayDataConst.SOURCE_KEY_YOUKU, "优酷视频网", "优酷", "YK", "https://www.youku.com/");
		saveSourcesData(PlayDataConst.SOURCE_KEY_QQ, "腾讯视频", "腾讯视频", "TX", "https://v.qq.com/");
		saveSourcesData(PlayDataConst.SOURCE_KEY_SOHU, "搜狐视频", "搜狐视频", "SH", "https://tv.sohu.com/");

		// 初始化要抓取的页面
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OVERLORDⅡ", "program", "", "https://bangumi.bilibili.com/anime/21466");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 1", "episode", "OVERLORDⅡ 1", "https://www.bilibili.com/bangumi/play/ep173248");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 2", "episode", "OVERLORDⅡ 2", "https://www.bilibili.com/bangumi/play/ep173249");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 3", "episode", "OVERLORDⅡ 3", "https://www.bilibili.com/bangumi/play/ep173250");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 4", "episode", "OVERLORDⅡ 4", "https://www.bilibili.com/bangumi/play/ep173251");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 5", "episode", "OVERLORDⅡ 5", "https://www.bilibili.com/bangumi/play/ep173252");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 6", "episode", "OVERLORDⅡ 6", "https://www.bilibili.com/bangumi/play/ep173253");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 7", "episode", "OVERLORDⅡ 7", "https://www.bilibili.com/bangumi/play/ep173254");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 8", "episode", "OVERLORDⅡ 8", "https://www.bilibili.com/bangumi/play/ep173255");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 9", "episode", "OVERLORDⅡ 9", "https://www.bilibili.com/bangumi/play/ep173256");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "OverloadII 10", "episode", "OVERLORDⅡ 10", "https://www.bilibili.com/bangumi/play/ep173257");

		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰", "program", "", "https://bangumi.bilibili.com/anime/21542");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 1", "episode", "", "https://www.bilibili.com/bangumi/play/ep173286");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 2", "episode", "", "https://www.bilibili.com/bangumi/play/ep173287");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 3", "episode", "", "https://www.bilibili.com/bangumi/play/ep173288");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 4", "episode", "", "https://www.bilibili.com/bangumi/play/ep173289");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 5", "episode", "", "https://www.bilibili.com/bangumi/play/ep173290");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 6", "episode", "", "https://www.bilibili.com/bangumi/play/ep173291");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 7", "episode", "", "https://www.bilibili.com/bangumi/play/ep173292");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 8", "episode", "", "https://www.bilibili.com/bangumi/play/ep173293");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 9", "episode", "", "https://www.bilibili.com/bangumi/play/ep173294");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "紫罗兰 10", "episode", "", "https://www.bilibili.com/bangumi/play/ep173295");

		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常", "program", "", "https://bangumi.bilibili.com/anime/21719");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 1", "episode", "", "https://www.bilibili.com/bangumi/play/ep172166");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 2", "episode", "", "https://www.bilibili.com/bangumi/play/ep172167");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 3", "episode", "", "https://www.bilibili.com/bangumi/play/ep172168");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 4", "episode", "", "https://www.bilibili.com/bangumi/play/ep172169");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 5", "episode", "", "https://www.bilibili.com/bangumi/play/ep172170");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 6", "episode", "", "https://www.bilibili.com/bangumi/play/ep172171");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 7", "episode", "", "https://www.bilibili.com/bangumi/play/ep172172");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 8", "episode", "", "https://www.bilibili.com/bangumi/play/ep172173");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 9", "episode", "", "https://www.bilibili.com/bangumi/play/ep172174");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "pop子和pipi美的日常 10", "episode", "", "https://www.bilibili.com/bangumi/play/ep172175");

		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "3月的狮子 第二季", "program", "", "https://bangumi.bilibili.com/anime/6445");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "3月的狮子 第二季 第24话 混沌/隈仓", "episode", "3月的狮子 第二季 第24话 混沌/隈仓", "https://www.bilibili.com/bangumi/play/ep115339");


		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "极乐净土】咬人猫/有咩酱/赤九玖❤155小分队o(*≧▽≦)ツ", "episode", "", "https://www.bilibili.com/video/av6117110");

		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "刻刻", "program", "", "https://bangumi.bilibili.com/anime/21755");
		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "龙王的工作", "program", "", "https://bangumi.bilibili.com/anime/21554");

		saveProcessUrlData(PlayDataConst.SOURCE_KEY_BILIBILI, "2018拜年祭", "2018拜年祭", "", "https://www.bilibili.com/blackboard/bnj2018.html");
		updateBatch();

		log.info("InitEntityDataRunner End");
	}


	private void saveSourcesData(String key, String name, String simpleName, String abName, String url) throws ParseException {
		String versionDef = "0.03";
		Date versionDate = DateUtil.fromString("2018-02-12 15:55:00");

		SourcesEntity sourcesEntity = allDaoUtil.getSourcesDao().findByKeyword(key);
		if (sourcesEntity == null) {
			sourcesEntity = new SourcesEntity();
			sourcesEntity.setKeyword(key);
		}

		sourcesEntity.setName(name);
		sourcesEntity.setSimpleName(simpleName);
		sourcesEntity.setAbName(abName);
		sourcesEntity.setUrl(url);
		sourcesEntity.setVersionDef(versionDef);
		sourcesEntity.setVersionDate(versionDate);

		if (!batchFlag) {
			allDaoUtil.getSourcesDao().save(sourcesEntity);
		} else {
			putBatchData(allDaoUtil.getSourcesDao(), sourcesEntity);
		}
	}

	private void saveProcessUrlData(String sourceKey, String name, String type, String desc, String url) {
		ProcessUrlEntity processUrlEntity = allDaoUtil.getProcessUrlDao().findBySourceKeyAndName(sourceKey, name);
		if (processUrlEntity == null) {
			processUrlEntity = new ProcessUrlEntity();
		}

		processUrlEntity.setSourceKey(sourceKey);
		processUrlEntity.setName(name);
		processUrlEntity.setType(type);
		processUrlEntity.setBeginDate(new Date());
		processUrlEntity.setUrl(url);
		processUrlEntity.setRemark(desc);

		if (!batchFlag) {
			allDaoUtil.getProcessUrlDao().save(processUrlEntity);
		} else {
			putBatchData(allDaoUtil.getProcessUrlDao(), processUrlEntity);
		}
	}


}