package xie.common.spring.printstartupinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import xie.common.spring.utils.SpringUtil;

import javax.annotation.Resource;

/**
 * 用于初始化一些基础数据
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class PrintInfoOnStart implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(PrintInfoOnStart.class);

	@Resource
	private ConfigurableEnvironment environment;
	@Resource
	private SpringUtil springUtil;

	@Override
	public void run(ApplicationArguments args) {
		springUtil.printNowProfilesListByEnvironment();
	}

}