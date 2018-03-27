package xie.common.spring.printstartupinfo.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import xie.common.spring.printstartupinfo.PrintInfoOnStartProperties;
import xie.common.spring.utils.InfoProperties;
import xie.common.spring.utils.SpringUtil;

import javax.annotation.Resource;

/**
 * 在启动成功后打印信息
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class PrintInfoAfterAppStart implements ApplicationRunner {

	@Resource
	private ConfigurableEnvironment environment;
	@Resource
	private SpringUtil springUtil;
	@Resource
	PrintInfoOnStartProperties printInfoOnStartProperties;

	@Override
	public void run(ApplicationArguments args) {
		if (printInfoOnStartProperties == null || !printInfoOnStartProperties.isNoPrintInfoAfterAppStart()) {
			springUtil.printNowProfilesListByEnvironment();
		}
	}

}