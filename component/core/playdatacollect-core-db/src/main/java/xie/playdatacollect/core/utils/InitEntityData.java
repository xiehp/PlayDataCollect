package xie.playdatacollect.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xie.common.date.DateUtil;
import xie.playdatacollect.base.db.entity.BaseEntity;
import xie.playdatacollect.base.db.repository.BaseDao;
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
public class InitEntityData implements ApplicationRunner {

	Logger log = LoggerFactory.getLogger(InitEntityData.class);

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
	}

	@Override
	public void run(ApplicationArguments args) throws ParseException {
		log.info("InitEntityData start");

		beginBatch();

		// 网站信息
		saveSourcesData("bilibili", "哔哩哔哩弹幕网", "哔哩哔哩", "BL", "https://www.bilibili.com/");
		saveSourcesData("iqiyi", "爱奇艺网", "爱奇艺", "IQY", "https://www.iqiyi.com/");
		saveSourcesData("youku", "优酷视频网", "优酷", "YK", "https://www.youku.com/");
		saveSourcesData("qq", "腾讯视频", "腾讯视频", "TX", "https://v.qq.com/");
		saveSourcesData("sohu", "搜狐视频", "搜狐视频", "SH", "https://tv.sohu.com/");

		// 初始化要抓取的页面
		saveProcessUrlData("bilibili", "OverloadII", "program", "", "https://bangumi.bilibili.com/anime/21466");
		saveProcessUrlData("bilibili", "OverloadII 1", "episode", "OVERLORDⅡ 1", "https://www.bilibili.com/bangumi/play/ep173248");
		saveProcessUrlData("bilibili", "OverloadII 2", "episode", "OVERLORDⅡ 2", "https://www.bilibili.com/bangumi/play/ep173249");
		saveProcessUrlData("bilibili", "OverloadII 3", "episode", "OVERLORDⅡ 3", "https://www.bilibili.com/bangumi/play/ep173250");
		saveProcessUrlData("bilibili", "OverloadII 4", "episode", "OVERLORDⅡ 4", "https://www.bilibili.com/bangumi/play/ep173251");
		saveProcessUrlData("bilibili", "OverloadII 5", "episode", "OVERLORDⅡ 5", "https://www.bilibili.com/bangumi/play/ep173252");
		saveProcessUrlData("bilibili", "OverloadII 5", "episode", "OVERLORDⅡ 6", "https://www.bilibili.com/bangumi/play/ep173253");
		saveProcessUrlData("bilibili", "OverloadII 5", "episode", "OVERLORDⅡ 7", "https://www.bilibili.com/bangumi/play/ep173254");

		saveProcessUrlData("bilibili", "紫罗兰", "program", "", "https://bangumi.bilibili.com/anime/21542");
		saveProcessUrlData("bilibili", "紫罗兰 1", "episode", "紫罗兰 1", "https://www.bilibili.com/bangumi/play/ep173286");
		saveProcessUrlData("bilibili", "紫罗兰 2", "episode", "紫罗兰 2", "https://www.bilibili.com/bangumi/play/ep173287");
		saveProcessUrlData("bilibili", "紫罗兰 3", "episode", "紫罗兰 3", "https://www.bilibili.com/bangumi/play/ep173288");
		saveProcessUrlData("bilibili", "紫罗兰 4", "episode", "紫罗兰 4", "https://www.bilibili.com/bangumi/play/ep173289");
		saveProcessUrlData("bilibili", "紫罗兰 5", "episode", "紫罗兰 5", "https://www.bilibili.com/bangumi/play/ep173290");
		saveProcessUrlData("bilibili", "紫罗兰 5", "episode", "紫罗兰 6", "https://www.bilibili.com/bangumi/play/ep173291");
		saveProcessUrlData("bilibili", "紫罗兰 5", "episode", "紫罗兰 7", "https://www.bilibili.com/bangumi/play/ep173292");

		saveProcessUrlData("bilibili", "3月的狮子 第二季", "program", "", "https://bangumi.bilibili.com/anime/6445");
		saveProcessUrlData("bilibili", "3月的狮子 第二季 第24话 混沌/隈仓", "episode", "3月的狮子 第二季 第24话 混沌/隈仓", "https://www.bilibili.com/bangumi/play/ep115339");


		saveProcessUrlData("bilibili", "极乐净土】咬人猫/有咩酱/赤九玖❤155小分队o(*≧▽≦)ツ", "video", "", "https://www.bilibili.com/video/av6117110");

		saveProcessUrlData("bilibili", "刻刻", "program", "", "https://bangumi.bilibili.com/anime/21755");
		saveProcessUrlData("bilibili", "龙王的工作", "program", "", "https://bangumi.bilibili.com/anime/21554");

		updateBatch();

		log.info("InitEntityData End");
	}


	private void saveSourcesData(String key, String name, String simpleName, String abName, String url) throws ParseException {
		String versionDef = "0.02";
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
		ProcessUrlEntity processUrlEntity = allDaoUtil.getProcessUrlDao().findByName(name);
		if (processUrlEntity == null) {
			processUrlEntity = new ProcessUrlEntity();
			processUrlEntity.setName(name);
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