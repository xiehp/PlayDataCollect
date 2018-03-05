package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.common.string.XStringUtils;
import xie.module.log.XLog;
import xie.playdatacollect.collector.quartz.job.Study1Job;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.playdata.cron")
public class QuartzConfig {

	String cronStudy1;

	public String getCronStudy1() {
		return cronStudy1;
	}

	public void setCronStudy1(String cronStudy1) {
		this.cronStudy1 = cronStudy1;
	}

	public JobDetail createJobDetail(Class clazz, String identity, JobDataMap jobDataMap) {
		return JobBuilder.newJob(clazz).withIdentity(identity)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

	public Trigger createCronTrigger(JobDetail jobDetail, String identity, String cron) {
		ScheduleBuilder scheduleBuilder = CronScheduleBuilder
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
	public JobDetail JobDetail_BiliBili_GetProcessUrl() {
		JobDataMap map = new JobDataMap();
		map.put("name", "JobDetail_BiliBili_GetProcessUrl");
		return createJobDetail(Study1Job.class, "JobDetail_BiliBili_GetProcessUrl", map);
	}

	@Bean
	public Trigger Trigger_BiliBili_GetProcessUrl() {
		String cron = "0 0/1 * * * ?";
		if (XStringUtils.isNotBlank(cronStudy1)) {
			cron = cronStudy1;
			XLog.info(this, "cron: {}", cron);
		} else {
			XLog.info(this, "cron配置不正确，使用默认: {}", cron);
		}

		return createCronTrigger(JobDetail_BiliBili_GetProcessUrl(), "Trigger_BiliBili_GetProcessUrl", cron);
	}
}
