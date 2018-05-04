package xie.playdatacollect.collector.quartz.config;

import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xie.common.spring.jpa.entity.BaseEntity;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.constant.XConst;
import xie.common.utils.log.XLog;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.collector.quartz.job.DummyJob;
import xie.playdatacollect.collector.quartz.job.NoJob;
import xie.playdatacollect.collector.quartz.job.XRefreshScheduleJob;
import xie.playdatacollect.collector.quartz.job.bilibili.*;
import xie.playdatacollect.collector.quartz.job.iqiyi.IQiYiGetProcessUrl;
import xie.playdatacollect.collector.quartz.job.iqiyi.IqiyiPlayDataProgramJob;
import xie.playdatacollect.collector.quartz.utils.XScheduleConfig;
import xie.playdatacollect.collector.quartz.utils.XRefreshSchedule;
import xie.playdatacollect.core.db.entity.schedule.ScheduleJobEntity;
import xie.playdatacollect.core.db.entity.schedule.ScheduleTriggerEntity;
import xie.playdatacollect.core.db.utils.AllDaoUtil;
import xie.playdatacollect.core.db.utils.InitEntityDataRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * 用于初始化一些基础数据
 */
@Component
@Order(0)
public class InitScheduleRunner extends InitEntityDataRunner {
//public class InitEntityDataRunner {

	Logger log = XLog.getLog(InitScheduleRunner.class);

	@Resource
	AllDaoUtil allDaoUtil;
	@Resource
	XRefreshSchedule refreshSchedule;

	private boolean batchFlag = false;
	private Map<BaseDao, List<BaseEntity>> batchDataMap = new LinkedHashMap<>();

	@Override
	public void run(ApplicationArguments args) throws ParseException {
		log.info("InitScheduleRunner start");

		initScheduleData();

		refreshSchedule.refreshSchedule();

		log.info("InitScheduleRunner End");
	}

	private void initScheduleData() throws ParseException {
		beginBatch();

		HashMap<String, Object> map = new HashMap<>();

		// sample
		saveJob("noJob", null, NoJob.class.getName(), map, null);
		map.put("name", "dummyJobDetail");
		map.put("sleep", 2L);
		saveJob("dummyJob", null, DummyJob.class.getName(), map, null);
		map.put("name", "dummyJobDetail1");
		map.put("sleep", 10L);
		saveJob("dummyJob1", null, DummyJob.class.getName(), map, null);
		map.put("name", "dummyJobDetail2");
		map.put("sleep", 20L);
		saveJob("dummyJob2", null, DummyJob.class.getName(), map, null);
		map.put("name", "dummyJobDetail3");
		map.put("sleep", 30L);
		saveJob("dummyJob3", null, DummyJob.class.getName(), map, null);
		map.put("name", "dummyJobDetail4");
		map.put("sleep", 40L);
		saveJob("dummyJob4", null, DummyJob.class.getName(), map, null);

		saveTrigger("trigger_noJob", null, "noJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_10_SECOND, null, true);
		saveTrigger("trigger_dummyJob", null, "dummyJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_03_SECOND, null, true);
		saveTrigger("trigger_dummyJob1", null, "dummyJob1", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_10_SECOND, null, true);
		saveTrigger("trigger_dummyJob2", null, "dummyJob2", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_20_SECOND, null, true);
		saveTrigger("trigger_dummyJob3", null, "dummyJob3", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_30_SECOND, null, true);
		saveTrigger("trigger_dummyJob4", null, "dummyJob4", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_40_SECOND, null, true);


		// schedule更新job
		saveJob("XRefreshScheduleJob", null, XRefreshScheduleJob.class.getName(), map, null);
		saveTrigger("trigger_XRefreshScheduleJob", null, "XRefreshScheduleJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_01_MIN, null, false);


		// TODO 这些最终应该在UI界面维护
		// bilibili
		map.put("name", "BilibiliPlayDataEpisodeNewJobDetail");
		saveJob("BilibiliPlayDataEpisodeNewJob", null, BilibiliPlayDataEpisodeNewJob.class.getName(), map, null);
		saveTrigger("trigger_EpisodeNewJob", null, "BilibiliPlayDataEpisodeNewJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_01_MIN, null, true);

		map.put("name", "BilibiliPlayDataEpisodeOldJobDetail");
		saveJob("BilibiliPlayDataEpisodeOldJob", null, BilibiliPlayDataEpisodeOldJob.class.getName(), map, null);
		saveTrigger("trigger_EpisodeOldJob", null, "BilibiliPlayDataEpisodeOldJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_10_MIN, null, true);

		map.put("name", "BilibiliPlayDataProgramJobDetail");
		saveJob("BilibiliPlayDataProgramJob", null, BilibiliPlayDataProgramJob.class.getName(), map, null);
		saveTrigger("trigger_ProgramJob", null, "BilibiliPlayDataProgramJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_05_MIN, null, false);

		map.put("name", "JobDetail_BiliBili_GetProcessUrl");
		saveJob("JobDetail_BiliBili_GetProcessUrl", null, BiliBiliGetProcessUrl.class.getName(), map, null);
		saveTrigger("Trigger_BiliBili_GetProcessUrl", null, "JobDetail_BiliBili_GetProcessUrl", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_12_HOUR, null, false);

		// BilibiliEpisode1
		map.put("name", "BilibiliEpisode1");
		map.put("beforeSecond", XConst.SECOND_03_DAY);
		map.put("afterSecond", -XConst.SECOND_02_MIN);
		saveJob("BilibiliEpisode1", null, BilibiliPlayDataEpisodeJob.class.getName(), map, null);
		saveTrigger("trigger_BilibiliEpisode1", null, "BilibiliEpisode1", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_01_MIN, null, false);
		// BilibiliEpisode2
		map.put("name", "BilibiliEpisode2");
		map.put("beforeSecond", XConst.SECOND_07_DAY);
		map.put("afterSecond", XConst.SECOND_03_DAY);
		saveJob("BilibiliEpisode2", null, BilibiliPlayDataEpisodeJob.class.getName(), map, null);
		saveTrigger("trigger_BilibiliEpisode2", null, "BilibiliEpisode2", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_05_MIN, null, false);
		// BilibiliEpisode3
		map.put("name", "BilibiliEpisode3");
		map.put("beforeSecond", XConst.SECOND_15_DAY);
		map.put("afterSecond", XConst.SECOND_07_DAY);
		saveJob("BilibiliEpisode3", null, BilibiliPlayDataEpisodeJob.class.getName(), map, null);
		saveTrigger("trigger_BilibiliEpisode3", null, "BilibiliEpisode3", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_10_MIN, null, false);
		// BilibiliEpisode4
		map.put("name", "BilibiliEpisode4");
		map.put("beforeSecond", XConst.SECOND_30_DAY);
		map.put("afterSecond", XConst.SECOND_15_DAY);
		saveJob("BilibiliEpisode4", null, BilibiliPlayDataEpisodeJob.class.getName(), map, null);
		saveTrigger("trigger_BilibiliEpisode4", null, "BilibiliEpisode4", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_20_MIN, null, false);
		// BilibiliEpisode5
		map.put("name", "BilibiliEpisode5");
		map.put("beforeSecond", XConst.SECOND_100_DAY);
		map.put("afterSecond", XConst.SECOND_30_DAY);
		saveJob("BilibiliEpisode5", null, BilibiliPlayDataEpisodeJob.class.getName(), map, null);
		saveTrigger("trigger_BilibiliEpisode5", null, "BilibiliEpisode5", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_30_MIN, null, false);
		// BilibiliEpisode6
		map.put("name", "BilibiliEpisode6");
		map.put("beforeSecond", null);
		map.put("afterSecond", XConst.SECOND_100_DAY);
		saveJob("BilibiliEpisode6", null, BilibiliPlayDataEpisodeJob.class.getName(), map, null);
		saveTrigger("trigger_BilibiliEpisode6", null, "BilibiliEpisode6", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_01_HOUR, null, false);

		// iqiyi
		map.clear();
		map.put("name", "JobDetail_Iqiyi_GetProcessUrl");
		saveJob("JobDetail_Iqiyi_GetProcessUrl", null, IQiYiGetProcessUrl.class.getName(), map, null);
		saveTrigger("Trigger_Iqiyi_GetProcessUrl", null, "JobDetail_Iqiyi_GetProcessUrl", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_12_HOUR, null, false);

		map.put("name", "IqiyiPlayDataProgramJob");
		saveJob("IqiyiPlayDataProgramJob", null, IqiyiPlayDataProgramJob.class.getName(), map, null);
		saveTrigger("trigger_IqiyiPlayDataProgramJob", null, "IqiyiPlayDataProgramJob", null, ScheduleTriggerEntity.TYPE_CRON, null, null, 0, 0, XScheduleConfig.PER_05_MIN, null, false);




		updateBatch();
	}

	private void saveJob(String identity, String group, String fullClassName, HashMap<String, Object> jsonData, HashMap<String, Object> jsonOptions) throws ParseException {
		if (XStringUtils.isBlank(group)) {
			group = "DEFAULT";
		}
		ScheduleJobEntity entity = allDaoUtil.getScheduleJobDao().findByIdentityAndGroupsAndVersionName(identity, group, XScheduleConfig.VERSION_NAME);
		if (entity == null) {
			entity = new ScheduleJobEntity();
		} else {
			Date dbVersionDate = entity.getVersionDate();
			if (dbVersionDate != null && dbVersionDate.compareTo(XScheduleConfig.VERSION_DATE) >= 0) {
				return;
			}
		}

		entity.setIdentity(identity);
		entity.setGroups(group);
		entity.setFullClassName(fullClassName);
		entity.setJsonData(XJsonUtil.toJsonString(jsonData));
		entity.setJsonOptions(XJsonUtil.toJsonString(jsonOptions));
		entity.setVersionName(XScheduleConfig.VERSION_NAME);
		entity.setVersionDate(XScheduleConfig.VERSION_DATE);

		if (!batchFlag) {
			allDaoUtil.getScheduleJobDao().save(entity);
		} else {
			putBatchData(allDaoUtil.getScheduleJobDao(), entity);
		}
	}

	private void saveTrigger(
			String identity
			, String group
			, String jobIdentity
			, String jobGroup
			, String type
			, Date startTime
			, Date endTime
			, int repeatCount
			, int repeatInterval
			, String cron
			, HashMap<String, Object> jsonOptions
			, boolean deleteFlag
			) throws ParseException {

		if (XStringUtils.isBlank(group)) {
			group = ScheduleTriggerEntity.GROUP_DEFAULT;
		}
		if (XStringUtils.isBlank(jobGroup)) {
			jobGroup = ScheduleTriggerEntity.GROUP_DEFAULT;
		}
		ScheduleTriggerEntity entity = allDaoUtil.getScheduleTriggerDao().findByIdentityAndGroupsAndVersionName(identity, group, XScheduleConfig.VERSION_NAME);
		if (entity == null) {
			entity = new ScheduleTriggerEntity();
		} else {
			Date dbVersionDate = entity.getVersionDate();
			if (dbVersionDate != null && dbVersionDate.compareTo(XScheduleConfig.VERSION_DATE) >= 0) {
				return;
			}
		}

		entity.setIdentity(identity);
		entity.setGroups(group);
		entity.setJobIdentity(jobIdentity);
		entity.setJobGroup(jobGroup);
		entity.setType(type);
		entity.setStartTime(startTime);
		entity.setEndTime(endTime);
		entity.setRepeatCount(repeatCount);
		entity.setRepeatInterval(repeatInterval);
		entity.setCron(cron);
		entity.setJsonOptions(XJsonUtil.toJsonString(jsonOptions));
		entity.setVersionName(XScheduleConfig.VERSION_NAME);
		entity.setVersionDate(XScheduleConfig.VERSION_DATE);
		if (deleteFlag) {
			entity.setDeleteFlag(BaseEntity.DELETE_FLAG_YES);
		}

		if (!batchFlag) {
			allDaoUtil.getScheduleTriggerDao().save(entity);
		} else {
			putBatchData(allDaoUtil.getScheduleTriggerDao(), entity);
		}
	}

}