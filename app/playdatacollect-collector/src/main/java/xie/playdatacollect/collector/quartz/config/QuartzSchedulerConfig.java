package xie.playdatacollect.collector.quartz.config;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import xie.module.quartz.XQuartzManager;
import xie.playdatacollect.collector.quartz.utils.XScheduleConfig;
import xie.playdatacollect.core.utils.AllDaoUtil;

import javax.annotation.Resource;

/**
 * @author xie
 */
@Configuration
@ConfigurationProperties("xie.quartz.trigger")
public class QuartzSchedulerConfig {

	@Resource
	AllDaoUtil daoUtil;

	@Bean
	public XQuartzManager getXQuartzManager(SchedulerFactoryBean schedulerFactoryBean) {
		XQuartzManager xQuartzManager = new XQuartzManager(schedulerFactoryBean);
		return xQuartzManager;
	}

	@Bean("SchedulerFactoryBeanCustomizer_set_instanceName")
	public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer_set_instanceName(QuartzProperties properties) {
		return (schedulerFactoryBean) -> {
			if (properties.getProperties().get("org.quartz.scheduler.instanceName") != null) {
				String scheduleName = properties.getProperties().get("org.quartz.scheduler.instanceName");
				//scheduleName = XScheduleConfig.VERSION_NAME;
				schedulerFactoryBean.setSchedulerName(scheduleName);
			}
		};
	}

	@Bean("schedulerFactoryBeanCustomizer_set_jobAndTrigger")
	public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer_set_jobAndTrigger(QuartzProperties properties) {
		return (schedulerFactoryBean) -> {
		};
	}
}
