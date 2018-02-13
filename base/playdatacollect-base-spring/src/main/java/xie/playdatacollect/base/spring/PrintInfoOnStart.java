package xie.playdatacollect.base.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import xie.module.spring.SpringUtil;

/**
 * 用于初始化一些基础数据
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class PrintInfoOnStart implements ApplicationRunner {

	private Logger log = LoggerFactory.getLogger(PrintInfoOnStart.class);

	@Autowired
	private ConfigurableEnvironment environment;
	@Autowired
	private SpringUtil springUtil;

	@Override
	public void run(ApplicationArguments args) {
		springUtil.printNowProfilesListByEnvironment();
	}

}