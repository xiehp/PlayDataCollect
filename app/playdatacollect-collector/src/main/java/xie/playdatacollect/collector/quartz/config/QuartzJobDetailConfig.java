package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xie.playdatacollect.collector.quartz.job.DummyJob;
import xie.playdatacollect.collector.quartz.job.NoJob;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.collector.quartz.job.bilibili.BiliBiliGetProcessUrl;
import xie.playdatacollect.collector.quartz.job.bilibili.BilibiliPlayDataEpisodeNewJob;
import xie.playdatacollect.collector.quartz.job.bilibili.BilibiliPlayDataEpisodeOldJob;
import xie.playdatacollect.collector.quartz.job.bilibili.BilibiliPlayDataProgramJob;
import xie.playdatacollect.collector.quartz.job.iqiyi.IQiYiGetProcessUrl;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.job")
public class QuartzJobDetailConfig {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public JobDetail createJobDetail(Class<? extends XBaseQuartzJobBean> clazz, String identity, JobDataMap jobDataMap) {
		return JobBuilder.newJob(clazz).withIdentity(identity)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

	@Bean
	public JobDetail noJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "noJobDetail");
		return createJobDetail(NoJob.class, "noJob", map);
	}

	@Bean
	public JobDetail dummyJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "dummyJobDetail");
		return createJobDetail(DummyJob.class, "dummyJob", map);
	}

	@Bean
	public JobDetail dummyJobDetail1() {
		JobDataMap map = new JobDataMap();
		map.put("name", "dummyJobDetail1");
		map.put("sleep", 10L);
		return createJobDetail(DummyJob.class, "dummyJob1", map);
	}

	@Bean
	public JobDetail dummyJobDetail2() {
		JobDataMap map = new JobDataMap();
		map.put("name", "dummyJobDetail2");
		map.put("sleep", 20L);
		return createJobDetail(DummyJob.class, "dummyJob2", map);
	}

	@Bean
	public JobDetail dummyJobDetail3() {
		JobDataMap map = new JobDataMap();
		map.put("name", "dummyJobDetail3");
		map.put("sleep", 30L);
		return createJobDetail(DummyJob.class, "dummyJob3", map);
	}

	@Bean
	public JobDetail dummyJobDetail4() {
		JobDataMap map = new JobDataMap();
		map.put("name", "dummyJobDetail4");
		map.put("sleep", 40L);
		return createJobDetail(DummyJob.class, "dummyJob4", map);
	}

	@Bean
	public JobDetail getBilibiliPlayDataEpisodeNewJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "BilibiliPlayDataEpisodeNewJobDetail");
		return createJobDetail(BilibiliPlayDataEpisodeNewJob.class, "BilibiliPlayDataEpisodeNewJob", map);
	}

	@Bean
	public JobDetail getBilibiliPlayDataEpisodeOldJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "BilibiliPlayDataEpisodeOldJobDetail");
		return createJobDetail(BilibiliPlayDataEpisodeOldJob.class, "BilibiliPlayDataEpisodeOldJob", map);
	}

	@Bean
	public JobDetail getBilibiliPlayDataProgramJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "BilibiliPlayDataProgramJobDetail");
		return createJobDetail(BilibiliPlayDataProgramJob.class, "BilibiliPlayDataProgramJob", map);
	}

	@Bean
	public JobDetail jobDetail_BiliBili_GetProcessUrl() {
		JobDataMap map = new JobDataMap();
		map.put("name", "JobDetail_BiliBili_GetProcessUrl");
		return createJobDetail(BiliBiliGetProcessUrl.class, "JobDetail_BiliBili_GetProcessUrl", map);
	}


	@Bean
	public JobDetail jobDetail_Iqiyi_GetProcessUrl() {
		JobDataMap map = new JobDataMap();
		map.put("name", "JobDetail_Iqiyi_GetProcessUrl");
		return createJobDetail(IQiYiGetProcessUrl.class, "JobDetail_Iqiyi_GetProcessUrl", map);
	}
}
