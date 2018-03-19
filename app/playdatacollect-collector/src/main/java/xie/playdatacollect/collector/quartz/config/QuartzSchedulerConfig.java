package xie.playdatacollect.collector.quartz.config;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import xie.common.string.XStringUtils;
import xie.module.log.XLog;
import xie.module.quartz.XQuartzManager;
import xie.playdatacollect.collector.quartz.job.BiliBiliGetProcessUrl;
import xie.playdatacollect.collector.quartz.job.DummyJob;
import xie.playdatacollect.collector.quartz.job.NoJob;
import xie.playdatacollect.collector.quartz.job.Study1Job;

import javax.annotation.Resource;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.trigger")
public class QuartzSchedulerConfig {

	@Bean
	public XQuartzManager getXQuartzManager(SchedulerFactoryBean schedulerFactoryBean) {
		XQuartzManager xQuartzManager = new XQuartzManager(schedulerFactoryBean);
		return xQuartzManager;
	}

	@Bean("SchedulerFactoryBeanCustomizer_set_instanceName")
	public SchedulerFactoryBeanCustomizer dataSourceCustomizer(QuartzProperties properties) {
		return (schedulerFactoryBean) -> {
			if (properties.getProperties().get("org.quartz.scheduler.instanceName") != null) {
				schedulerFactoryBean.setSchedulerName(properties.getProperties().get("org.quartz.scheduler.instanceName"));
			}
		};
	}//	}
}
