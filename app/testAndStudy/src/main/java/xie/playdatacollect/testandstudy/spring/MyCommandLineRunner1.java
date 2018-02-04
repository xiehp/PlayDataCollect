package xie.playdatacollect.testandstudy.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import xie.playdatacollect.core.service.MetricService;
import xie.playdatacollect.core.service.TagService;
import xie.playdatacollect.core.service.ValueService;

import javax.annotation.Resource;

/**
 * 用于初始化一些基础数据
 */
@Configuration
public class MyCommandLineRunner1 implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(MyCommandLineRunner1.class);

	@Resource
	TagService tagService;
	@Resource
	MetricService metricService;
	@Resource
	ValueService valueService;

	@Override
	public void run(String... args) {
		log.info("MyCommandLineRunner1 start");
	}

}