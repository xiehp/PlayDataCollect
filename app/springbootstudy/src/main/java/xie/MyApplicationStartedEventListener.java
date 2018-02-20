package xie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * spring boot 启动监听类
 */
@Component
public class MyApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		logger.error("getEnvironment:" + event.getApplicationContext().getEnvironment());
	}
}