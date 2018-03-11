package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.common.string.XStringUtils;
import xie.module.log.XLog;
import xie.playdatacollect.collector.quartz.job.*;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.job")
public class QuartzJobDetailConfig {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public JobDetail createJobDetail(Class clazz, String identity, JobDataMap jobDataMap) {
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
	public JobDetail sampleJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "sampleJobDetail");
		return createJobDetail(Study1Job.class, "sampleJob", map);
	}


	@Bean
	public JobDetail getBilibiliPlayDataEpisodeJobDetail() {
		JobDataMap map = new JobDataMap();
		map.put("name", "BilibiliPlayDataEpisodeJobDetail");
		return createJobDetail(BilibiliPlayDataEpisodeJob.class, "BilibiliPlayDataEpisodeJob", map);
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
}
