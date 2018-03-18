package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.playdatacollect.collector.quartz.utils.XCronConfig;

import javax.annotation.Resource;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.trigger")
public class QuartzTriggerConfig {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private QuartzJobDetailConfig jobConfig;

	private String cron1;
	private String cron2;
	private int doFlag = 1; // 0:不执行 1:执行 2:运行dummy

	public void setCron1(String cron1) {
		this.cron1 = cron1;
	}

	public void setCron2(String cron2) {
		this.cron2 = cron2;
	}

	public void setDoFlag(int doFlag) {
		this.doFlag = doFlag;
	}

	public Trigger createCronTrigger(JobDetail jobDetail, String identity, String cron) {

		logger.info("job:{}, cron:{}", ((JobDetailImpl) jobDetail).getFullName(), cron);
		ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder
				.cronSchedule(cron)
				.withMisfireHandlingInstructionDoNothing();

		return createTrigger(scheduleBuilder, jobDetail, identity);
	}

	public Trigger createTrigger(ScheduleBuilder scheduleBuilder, JobDetail jobDetail, String identity) {

		logger.info("job:{}, doFlag:{}", ((JobDetailImpl) jobDetail).getFullName(), doFlag);

		if (doFlag == 0) {
			jobDetail = jobConfig.noJobDetail();
		}
		if (doFlag == 2) {
			jobDetail = jobConfig.dummyJobDetail();
		}

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.forJob(jobDetail)
				.withIdentity(identity)
				.withSchedule(scheduleBuilder)
				.build();

		logger.info("trigger create: {}", trigger);
		return trigger;
	}

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
//		return createCronTrigger(jobConfig.sampleJobDetail(), "sampleTrigger", cron);
//	}

//	@Bean
	public Trigger trigger_ProgramJob() {
		return createCronTrigger(jobConfig.getBilibiliPlayDataProgramJobDetail(), "trigger_ProgramJob", XCronConfig.PER_05_MIN);
	}

//	@Bean
	public Trigger trigger_EpisodeJob() {
		return createCronTrigger(jobConfig.getBilibiliPlayDataEpisodeJobDetail(), "trigger_EpisodeJob", cron1);
	}

//	@Bean
	public Trigger trigger_BiliBili_GetProcessUrl_per5day() {
		return createCronTrigger(jobConfig.jobDetail_BiliBili_GetProcessUrl(), "Trigger_BiliBili_GetProcessUrl", XCronConfig.PER_10_HOUR);
	}

//	@Bean
	public Trigger trigger_BiliBili_GetProcessUrl_OnStart() {

		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForTotalCount(1);

		return createTrigger(scheduleBuilder, jobConfig.jobDetail_BiliBili_GetProcessUrl(), "trigger_BiliBili_GetProcessUrl_OnStart");
	}
}
