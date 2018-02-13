package xie.playdatacollect.base.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;

/**
 * spring boot 启动监听类
 */
public class MyApplicationStartedEventListener extends XApplicationListener<ApplicationStartedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		logger.info("==MyApplicationStartedEventListener==, getEnvironment:" + event.getApplicationContext().getEnvironment());
	}
}