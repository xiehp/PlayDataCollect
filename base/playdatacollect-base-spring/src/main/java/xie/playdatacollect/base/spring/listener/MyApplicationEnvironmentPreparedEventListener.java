package xie.playdatacollect.base.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;

/**
 * spring boot 启动监听类
 */
public class MyApplicationEnvironmentPreparedEventListener extends XApplicationListener<ApplicationEnvironmentPreparedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		logger.info("getEnvironment:" + event.getEnvironment());
	}
}