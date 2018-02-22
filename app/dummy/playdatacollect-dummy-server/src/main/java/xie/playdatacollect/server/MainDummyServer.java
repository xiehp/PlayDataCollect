package xie.playdatacollect.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xie.module.log.XLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@EnableAutoConfiguration
public class MainDummyServer {
	Logger log = XLog.getLogger(this.getClass());

	@RequestMapping("/")
	public String index() {
		return "This is Dummy Server!";
	}


	private int site1_getPayCount_request_count = 0;
	private Random random = new Random(100);

	@RequestMapping("/site1/getPayCount")
	@ResponseBody
	public Map<String, Object> site1_getPayCount(
			@RequestParam(required = false) String title) {
		int count = random.nextInt(10) + site1_getPayCount_request_count;
		site1_getPayCount_request_count = count;

		Map<String, Object> getPayCount = new HashMap<>();
		getPayCount.put("title", StringUtils.isEmpty(title) ? "the title" : title);
		getPayCount.put("payCount", count);

		//log.info(getPayCount.toString());

		//XLog.info(getPayCount.toString());
		//XLog.getXLogger().info(getPayCount.toString());

		XLog.info(this, getPayCount.toString());
		return getPayCount;
	}

	public static void main(String[] args) {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");

		SpringApplication.run(MainDummyServer.class, args);
	}
}