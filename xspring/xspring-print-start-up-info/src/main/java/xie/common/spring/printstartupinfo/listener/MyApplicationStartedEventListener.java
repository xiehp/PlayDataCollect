package xie.common.spring.printstartupinfo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;

/**
 * spring boot 启动成功监听类
 */
@Component
public class MyApplicationStartedEventListener extends XApplicationListener<ApplicationStartedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		logger.info("Environment:" + event.getApplicationContext().getEnvironment());
	}
}