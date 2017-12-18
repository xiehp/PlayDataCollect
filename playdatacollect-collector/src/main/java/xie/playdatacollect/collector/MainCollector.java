package xie.playdatacollect.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MainCollector {

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "This is Collector!";
	}

	public static void main(String[] args) throws Exception {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		int a = 1;
		while (true) {
			a++;
			if (a > 10) {
				break;
			}
			System.out.println(0111);
		}
		SpringApplication.run(MainCollector.class, args);
	}
}
