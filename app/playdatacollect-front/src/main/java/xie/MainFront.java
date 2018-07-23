package xie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.common.utils.date.XDateUtil;
import xie.component.httpclient.XHttpClientUtils;
import xie.playdatacollect.core.db.service.MetricService;
import xie.playdatacollect.core.db.service.TagService;
import xie.playdatacollect.core.db.service.ValueService;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Service;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@SpringBootApplication
@RestController
//@ComponentScan("xie")
//@EntityScan("xie")
//@EnableJpaRepositories(basePackages = "xie")
public class MainFront {

	@Resource
	RestTemplate restTemplate;
	@Resource
	XHttpClientUtils xHttpClientUtils;

	@Resource
	Test1Service test1Service;
	@Resource
	Test2Service test2Service;
	@Resource
	TagService tagService;
	@Resource
	MetricService metricService;
	@Resource
	ValueService valueService;

	@Resource
	XInfluxdbAction xInfluxdbAction;

	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

//	@RequestMapping("/")
//	public String index() {
//		return "This is Front!!";
//	}

	static String tempId = "";

	@RequestMapping("/getAndSaveData")
	public Object getAndSaveData() {

		Map aaa = restTemplate.postForObject("http://localhost:15001/site1/getPayCount", null, HashMap.class);

		return aaa;
	}

	/**
	 * 从0点开始,每2个小时执行一次
	 */
//	@Scheduled(cron = "0/10 * * * * ?")
//	@Scheduled(cron = "0 0/1 * * * ?")
//	public void runScheduled() {
//		System.out.println(new Date() + "----开始执行定时抓取任务");
//
//
//		System.out.println("抓取任务 end");
//	}


	public static void main(String[] args) {
		// 设置时区
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		System.out.println(XDateUtil.formatTime(new Date().getTime(), "HH:mm:ss"));

		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		ConfigurableApplicationContext run = SpringApplication.run(MainFront.class, args);
		String property = run.getEnvironment().getProperty("ds.password");
		System.out.println(property);
	}
}
