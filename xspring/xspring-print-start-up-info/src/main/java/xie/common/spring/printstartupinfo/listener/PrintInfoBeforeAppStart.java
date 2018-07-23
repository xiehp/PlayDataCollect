package xie.common.spring.printstartupinfo.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.stereotype.Component;
import xie.common.spring.printstartupinfo.PrintInfoOnStartProperties;
import xie.common.spring.utils.InfoProperties;
import xie.common.spring.utils.SpringUtil;
import xie.common.utils.log.XLog;

import javax.annotation.Resource;

/**
 * spring boot环境准备后，应用启动前的监听事件<br />
 * <br />
 * 由于还未开始创建bean，因此该方法由META-INF\spring.factories文件进行配置<br />
 * key为org.springframework.context.ApplicationListener<br />
 */
@Component
public class PrintInfoBeforeAppStart extends XApplicationListener<ApplicationEnvironmentPreparedEvent> {

	// TODO 因为还未初始化bean，所以在这里是没有作用的
	@Resource
	PrintInfoOnStartProperties printInfoOnStartProperties;

	@Resource
	InfoProperties infoProperties;

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		XLog.info(this, "PrintInfoBeforeAppStart start!");
		if (printInfoOnStartProperties == null || !printInfoOnStartProperties.isNoPrintInfoBeforeAppStart()) {
			SpringUtil.printNowProfilesListByEnvironment(event.getEnvironment(), infoProperties);
		}
		XLog.info(this, "PrintInfoBeforeAppStart end!");
	}
}