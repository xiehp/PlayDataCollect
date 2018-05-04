package xie.playdatacollect.collector.quartz.utils;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.collection.XListUtils;
import xie.common.utils.log.XLog;
import xie.common.utils.string.XStringUtils;
import xie.module.quartz.XQuartzManager;
import xie.playdatacollect.collector.quartz.config.QuartzJobDetailConfig;
import xie.playdatacollect.collector.quartz.config.QuartzTriggerConfig;
import xie.playdatacollect.core.db.entity.schedule.ScheduleJobEntity;
import xie.playdatacollect.core.db.entity.schedule.ScheduleTriggerEntity;
import xie.playdatacollect.core.db.utils.AllDaoUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class XRefreshSchedule {

	List<ScheduleJobEntity> jobList;
	List<ScheduleTriggerEntity> triggerList;

	@Resource
	AllDaoUtil allDaoUtil;
	@Resource
	XQuartzManager xQuartzManager;

	public boolean refreshSchedule() {
		boolean doRefresh = true;
		if (!doRefresh) {
			return true;
		}

		List<ScheduleJobEntity> jobList = allDaoUtil.getScheduleJobDao().findByVersionName(XScheduleConfig.VERSION_NAME);
		List<ScheduleTriggerEntity> triggerList = allDaoUtil.getScheduleTriggerDao().findByVersionName(XScheduleConfig.VERSION_NAME);
		for (ScheduleTriggerEntity triggerEntity : triggerList) {
			// 判断该trigger是否已经配置过了
			if (triggerEntity.getVersionDate() != null && triggerEntity.getVersionSetupDate() != null) {
				if (triggerEntity.getVersionSetupDate().compareTo(triggerEntity.getVersionDate()) >= 0) {
					// 配置时间大于等于版本时间，就不需要刷新了
					XLog.getLog(this).info("{}跳过配置", triggerEntity.getIdentity());
					continue;
				}
			}

			final String jobIdentity = triggerEntity.getJobIdentity();
			final String jobGroup = triggerEntity.getJobGroup();

			if (ScheduleTriggerEntity.DELETE_FLAG_YES.equals(triggerEntity.getDeleteFlag())) {
				try {
					xQuartzManager.unscheduleJob(triggerEntity.getIdentity(), triggerEntity.getGroups());
				} catch (SchedulerException e) {
					XLog.getLog(this).error("停止trigger发生错误", e);
				}
				continue;
			} else {
				ScheduleJobEntity jobEntity = XListUtils.getValueByKey(jobList, (job) -> {
					if (XStringUtils.equalsIgnoreCase(jobIdentity, job.getIdentity())
							&& XStringUtils.equalsIgnoreCase(jobGroup, job.getGroups())) {
						return true;
					} else {
						return false;
					}
				});
				if (jobEntity == null) {
					XLog.getLog(this).error("trigger {}({}) 对应 job {}({}) 不存在", triggerEntity.getIdentity(), triggerEntity.getGroups(), jobIdentity, jobGroup);
				} else {
					try {
						Class jobClass = Class.forName(jobEntity.getFullClassName());
						JobDataMap jobDataMap = null;
						if (XStringUtils.isNotBlank(jobEntity.getJsonData())) {
							jobDataMap = XJsonUtil.fromJsonString(jobEntity.getJsonData(), JobDataMap.class);
						}
						JobDetail jobDetail = QuartzJobDetailConfig.createJobDetail(jobClass, jobIdentity, jobGroup, jobDataMap);
						Trigger trigger = QuartzTriggerConfig.createCronTrigger(jobDetail, triggerEntity.getIdentity(), triggerEntity.getGroups(), triggerEntity.getCron());

						xQuartzManager.startTrigger(jobDetail, trigger);
					} catch (Exception e) {
						XLog.getLog(this).error("发生错误", e);
					}
				}
			}

			// 更新配置时间
			triggerEntity.setVersionSetupDate(new Date());
			allDaoUtil.getScheduleTriggerDao().save(triggerEntity);
			XLog.getLog(this).info("{}已配置", triggerEntity.getIdentity());
		}


		return true;
	}
}
