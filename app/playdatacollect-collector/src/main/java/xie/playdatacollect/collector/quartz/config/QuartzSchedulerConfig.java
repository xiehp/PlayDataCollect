package xie.playdatacollect.collector.quartz.config;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import xie.module.quartz.XQuartzManager;

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
