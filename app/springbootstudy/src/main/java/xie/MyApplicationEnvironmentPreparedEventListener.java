package xie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import xie.common.spring.utils.SpringUtil;

/**
 * spring boot 启动监听类
 */
@Component
public class MyApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		logger.error("getEnvironment:" + event.getEnvironment());

		SpringUtil.printNowProfilesListByEnvironment(event.getEnvironment());
	}
}