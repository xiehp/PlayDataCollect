package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.common.string.XStringUtils;
import xie.module.log.XLog;
import xie.playdatacollect.collector.quartz.job.BiliBiliGetProcessUrl;
import xie.playdatacollect.collector.quartz.job.DummyJob;
import xie.playdatacollect.collector.quartz.job.NoJob;
import xie.playdatacollect.collector.quartz.job.Study1Job;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.trigger")
public class QuartzConfig {
//
//	Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	private String cron1;
//	private String cron2;
//	private int doFlag = 1; // 0:不执行 1:执行 2:运行dummy
//
//	public void setCron1(String cron1) {
//		this.cron1 = cron1;
//	}
//
//	public void setCron2(String cron2) {
//		this.cron2 = cron2;
//	}
//
//	public void setDoFlag(int doFlag) {
//		this.doFlag = doFlag;
//	}
//
//	private JobDetail createJobDetail(Class clazz, String identity, JobDataMap jobDataMap) {
//		return JobBuilder.newJob(clazz).withIdentity(identity)
//				.usingJobData(jobDataMap)
//				.storeDurably()
//				.build();
//	}
//
//	private Trigger createCronTrigger(JobDetail jobDetail, String identity, String cron) {
//
//		ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder
//				.cronSchedule(cron)
//				.withMisfireHandlingInstructionDoNothing();
//
//		return createTrigger(scheduleBuilder, jobDetail, identity);
//	}
//
//	private Trigger createTrigger(ScheduleBuilder schedule, JobDetail jobDetail, String identity) {
//
//		XLog.info(this, doFlag + "");
//
//		if (doFlag == 0) {
//			jobDetail = noJobDetail();
//		}
//		if (doFlag == 2) {
//			jobDetail = dummyJobDetail();
//		}
//
//		Trigger trigger = TriggerBuilder
//				.newTrigger()
//				.forJob(jobDetail)
//				.withIdentity(identity)
//				.withSchedule(schedule)
//				.build();
//
//		logger.info("trigger create: {}", trigger);
//		return trigger;
//	}
//
//	@Bean
//	public JobDetail noJobDetail() {
//		JobDataMap map = new JobDataMap();
//		map.put("name", "noJobDetail");
//		return createJobDetail(NoJob.class, "noJob", map);
//	}
//
//	@Bean
//	public JobDetail dummyJobDetail() {
//		JobDataMap map = new JobDataMap();
//		map.put("name", "dummyJobDetail");
//		return createJobDetail(DummyJob.class, "dummyJob", map);
//	}
//
//	@Bean
//	public JobDetail sampleJobDetail() {
//		JobDataMap map = new JobDataMap();
//		map.put("name", "sampleJobDetail");
//		return createJobDetail(Study1Job.class, "sampleJob", map);
//	}
//
//	@Bean
//	public Trigger sampleJobTrigger() {
//		String cron = "0 0/1 * * * ?";
//		if (XStringUtils.isNotBlank(cron1)) {
//			cron = cron1;
//			XLog.info(this, "cron: {}", cron);
//		} else {
//			XLog.info(this, "cron配置不正确，使用默认: {}", cron);
//		}
//
//		return createCronTrigger(sampleJobDetail(), "sampleTrigger", cron);
//	}
//
//
//	@Bean
//	public JobDetail getBilibiliPlayDataEpisodeJobDetail() {
//		JobDataMap map = new JobDataMap();
//		map.put("name", "BilibiliPlayDataEpisodeJobDetail");
//		return createJobDetail(Study1Job.class, "BilibiliPlayDataEpisodeJob", map);
//	}
//
//	@Bean
//	public JobDetail getBilibiliPlayDataProgramJobDetail() {
//		JobDataMap map = new JobDataMap();
//		map.put("name", "BilibiliPlayDataProgramJobDetail");
//		return createJobDetail(Study1Job.class, "BilibiliPlayDataProgramJob", map);
//	}
//
//	@Bean
//	public JobDetail jobDetail_BiliBili_GetProcessUrl() {
//		JobDataMap map = new JobDataMap();
//		map.put("name", "JobDetail_BiliBili_GetProcessUrl");
//		return createJobDetail(BiliBiliGetProcessUrl.class, "JobDetail_BiliBili_GetProcessUrl", map);
//	}
//
//	@Bean
//	public Trigger trigger_BiliBili_GetProcessUrl_per5day() {
//		String cron = "0 0 0 1/5 * ? *";
//		if (XStringUtils.isNotBlank(cron2)) {
//			cron = cron2;
//			XLog.info(this, "cron: {}", cron);
//		} else {
//			XLog.info(this, "cron配置不正确，使用默认: {}", cron);
//		}
//
//		return createCronTrigger(jobDetail_BiliBili_GetProcessUrl(), "Trigger_BiliBili_GetProcessUrl", cron);
//	}
//
//	@Bean
//	public Trigger trigger_BiliBili_GetProcessUrl_OnStart() {
//
//		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForTotalCount(1);
//
//		return createTrigger(scheduleBuilder, jobDetail_BiliBili_GetProcessUrl(), "trigger_BiliBili_GetProcessUrl_OnStart");
//	}
}
