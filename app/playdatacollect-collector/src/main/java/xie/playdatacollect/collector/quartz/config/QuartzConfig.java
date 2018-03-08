package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.common.string.XStringUtils;
import xie.module.log.XLog;
import xie.playdatacollect.collector.quartz.job.BiliBiliGetProcessUrl;
import xie.playdatacollect.collector.quartz.job.Study1Job;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.playdata.cron")
public class QuartzConfig {

	private String cronStudy1;

	public String getCronStudy1() {
		return cronStudy1;
	}

	public void setCronStudy1(String cronStudy1) {
		this.cronStudy1 = cronStudy1;
	}

	private JobDetail createJobDetail(Class clazz, String identity, JobDataMap jobDataMap) {
		return JobBuilder.newJob(clazz).withIdentity(identity)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

	private Trigger createCronTrigger(JobDetail jobDetail, String identity, String cron) {
		ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder
				.cronSchedule(cron)
				.withMisfireHandlingInstructionDoNothing();

		return TriggerBuilder
				.newTrigger()
				.forJob(jobDetail)
				.withIdentity(identity)
				.withSchedule(scheduleBuilder)
				.build();
	}

	@Bean
	public JobDetail sampleJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "sampleJobDetail");
		return createJobDetail(Study1Job.class, "sampleJob", map);
	}

	@Bean
	public Trigger sampleJobTrigger() {
		String cron = "0 0/1 * * * ?";
		if (XStringUtils.isNotBlank(cronStudy1)) {
			cron = cronStudy1;
			XLog.info(this, "cron: {}", cron);
		} else {
			XLog.info(this, "cron配置不正确，使用默认: {}", cron);
		}

		return createCronTrigger(sampleJobDetail(), "sampleTrigger", cron);
	}


	@Bean
	public JobDetail jobDetail_BiliBili_GetProcessUrl() {
		JobDataMap map = new JobDataMap();
		map.put("name", "JobDetail_BiliBili_GetProcessUrl");
		return createJobDetail(BiliBiliGetProcessUrl.class, "JobDetail_BiliBili_GetProcessUrl", map);
	}

	@Bean
	public Trigger trigger_BiliBili_GetProcessUrl_per5day() {
		String cron = "0 0 0 1/5 * ? *";
//		if (XStringUtils.isNotBlank(cronStudy1)) {
//			cron = cronStudy1;
//			XLog.info(this, "cron: {}", cron);
//		} else {
//			XLog.info(this, "cron配置不正确，使用默认: {}", cron);
//		}

		return createCronTrigger(jobDetail_BiliBili_GetProcessUrl(), "Trigger_BiliBili_GetProcessUrl", cron);
	}

	@Bean
	public Trigger trigger_BiliBili_GetProcessUrl_OnStart() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForTotalCount(1);

		return TriggerBuilder
				.newTrigger()
				.forJob(jobDetail_BiliBili_GetProcessUrl())
				.withIdentity("trigger_BiliBili_GetProcessUrl_OnStart")
				.withSchedule(scheduleBuilder)
				.build();
	}
}
