package xie.common.spring.printstartupinfo.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import xie.common.spring.utils.SpringUtil;

/**
 * spring boot 环境准备后好后监听事件<br>
 * 由于还未开始创建bean，因此该方法由META-INF\spring.factories文件进行配置<br>
 * key为org.springframework.context.ApplicationListener<br>
 */
public class MyApplicationEnvironmentPreparedEventListener extends XApplicationListener<ApplicationEnvironmentPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		SpringUtil.printNowProfilesListByEnvironment(event.getEnvironment());
	}
}