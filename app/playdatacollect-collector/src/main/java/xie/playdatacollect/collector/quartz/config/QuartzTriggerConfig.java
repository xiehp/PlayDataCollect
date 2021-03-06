package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.trigger")
public class QuartzTriggerConfig {

	private static final Logger logger = LoggerFactory.getLogger(QuartzTriggerConfig.class);

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

	public static Trigger createCronTrigger(JobDetail jobDetail, String identity, String cron) {
		return createCronTrigger(jobDetail, identity, null, cron);
	}

	public static Trigger createCronTrigger(JobDetail jobDetail, String identity, String group, String cron) {

		logger.info("job:{}, cron:{}", ((JobDetailImpl) jobDetail).getFullName(), cron);
		ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder
				.cronSchedule(cron)
				.withMisfireHandlingInstructionDoNothing();

		return createTrigger(scheduleBuilder, jobDetail, identity);
	}

	public static Trigger createCronTrigger(JobDetail jobDetail, String identity, String group, String cron, Date startDate, Date endDate) {

		logger.info("job:{}, cron:{}", ((JobDetailImpl) jobDetail).getFullName(), cron);
		ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder
				.cronSchedule(cron)
				.withMisfireHandlingInstructionDoNothing();

		return createTrigger(scheduleBuilder, jobDetail, identity);
	}

	public static Trigger createTrigger(ScheduleBuilder scheduleBuilder, JobDetail jobDetail, String identity) {

//		logger.info("job:{}, doFlag:{}", ((JobDetailImpl) jobDetail).getFullName(), doFlag);

//		if (doFlag == 0) {
//			jobDetail = jobConfig.noJobDetail();
//		}
//		if (doFlag == 2) {
//			jobDetail = jobConfig.dummyJobDetail();
//		}

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
//		return createCronTrigger(jobConfig.dummyJobDetail1(), "sampleTrigger", cron);
//	}

//	@Bean
//	public Trigger trigger_ProgramJob() {
//		return createCronTrigger(jobConfig.getBilibiliPlayDataProgramJobDetail(), "trigger_ProgramJob", XScheduleConfig.PER_05_MIN);
//	}
//
//	@Bean
//	public Trigger trigger_EpisodeNewJob() {
//		return createCronTrigger(jobConfig.getBilibiliPlayDataEpisodeNewJobDetail(), "trigger_EpisodeNewJob", XScheduleConfig.PER_01_MIN);
//	}
//
//	@Bean
//	public Trigger trigger_EpisodeOldJob() {
//		return createCronTrigger(jobConfig.getBilibiliPlayDataEpisodeOldJobDetail(), "trigger_EpisodeOldJob", XScheduleConfig.PER_05_MIN);
//	}
//
//	@Bean
//	public Trigger trigger_BiliBili_GetProcessUrl_loop() {
//		return createCronTrigger(jobConfig.jobDetail_BiliBili_GetProcessUrl(), "Trigger_BiliBili_GetProcessUrl", XScheduleConfig.PER_12_HOUR);
//	}

//	@Bean
	public Trigger trigger_BiliBili_GetProcessUrl_OnStart() {

		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForTotalCount(1);

		return createTrigger(scheduleBuilder, jobConfig.jobDetail_BiliBili_GetProcessUrl(), "trigger_BiliBili_GetProcessUrl_OnStart");
	}


//	@Bean
//	public Trigger trigger_Iqiyi_GetProcessUrl_loop() {
//		return createCronTrigger(jobConfig.jobDetail_Iqiyi_GetProcessUrl(), "Trigger_Iqiyi_GetProcessUrl", XScheduleConfig.PER_12_HOUR);
//	}

//	@Bean
	public Trigger trigger_Iqiyi_GetProcessUrl_OnStart() {

		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForTotalCount(1);

		return createTrigger(scheduleBuilder, jobConfig.jobDetail_Iqiyi_GetProcessUrl(), "trigger_Iqiyi_GetProcessUrl_OnStart");
	}
}
