package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import xie.common.string.XStringUtils;
import xie.playdatacollect.collector.job.Study1Job;

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

	@Bean
	public JobDetail sampleJobDetail() {
		return JobBuilder.newJob(Study1Job.class).withIdentity("sampleJob")
				.usingJobData("name", "World").storeDurably().build();
	}

	@Bean
	public Trigger sampleJobTrigger() {
		ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
		if (XStringUtils.isNotBlank(cronStudy1)) {
			scheduleBuilder = CronScheduleBuilder.cronSchedule(cronStudy1);
		}

		return TriggerBuilder.newTrigger().forJob(sampleJobDetail())
				.withIdentity("sampleTrigger").withSchedule(scheduleBuilder).build();
	}
}
