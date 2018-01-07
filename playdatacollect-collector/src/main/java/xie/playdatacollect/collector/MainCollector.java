package xie.playdatacollect.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xie.playdatacollect.collector.fun.test1.Test1.Test1Dao;
import xie.playdatacollect.collector.fun.test1.Test1.Test1Entity;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
//@ImportResource("applicationContext.xml")
public class MainCollector {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	Test1Dao test1Dao;

	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/")
	public String index() {
		return "This is Collector!";
	}

	@RequestMapping("/getAndSaveData")
	public Object getAndSaveData() {

		Object aaa = restTemplate.postForObject("http://localhost:15001/site1/getPayCount", null, Object.class);

		Test1Entity entity = new Test1Entity();
		entity.setCol1(aaa.toString());
		Test1Entity newEntity = test1Dao.save(entity);
		Test1Entity newEntity2 = test1Dao.findById(newEntity.getId()).get();

		List<Object> aaaa = new ArrayList<>();
		aaaa.add(aaa);
		aaaa.add(entity);
		aaaa.add(newEntity);
		aaaa.add(newEntity2);
		return aaaa;
	}


	public static void main(String[] args) {
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
