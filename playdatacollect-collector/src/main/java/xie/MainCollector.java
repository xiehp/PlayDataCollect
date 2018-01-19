package xie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Dao;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Dao;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@EnableAutoConfiguration
//@ImportResource("applicationContext.xml")
@SpringBootApplication
@ComponentScan(basePackages = {"xie"})
public class MainCollector {

	@Autowired
	RestTemplate restTemplate;
//	@Autowired
//	XHttpClientUtils xHttpClientUtils;

	@Autowired
	Test1Dao test1Dao;
	@Autowired
	Test2Dao test2Dao;

	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/")
	public String index() {
		return "This is Collector!";
	}

	static String tempId = "";

	@RequestMapping("/getAndSaveData")
	public Object getAndSaveData() throws IOException {

		Map aaa = restTemplate.postForObject("http://localhost:15001/site1/getPayCount", null, HashMap.class);


		Test1Entity entity = new Test1Entity();
		entity.setCol1(aaa.toString());
		Test1Entity newEntity = test1Dao.save(entity);
		Test1Entity newEntity_ = test1Dao.findById(newEntity.getId()).get();


		Test2Entity entity2 = new Test2Entity();
		entity2.setCol2(aaa.get("title").toString());
		entity2.setCol1(aaa.get("payCount").toString());
		Test2Entity newEntity2 = test2Dao.save(entity2);
		Test2Entity newEntity2_ = test2Dao.findById(newEntity2.getId()).get();

		List<Object> aaaa = new ArrayList<>();
		aaaa.add(aaa);
		aaaa.add(entity);
		aaaa.add(newEntity);
		aaaa.add(newEntity_);
		aaaa.add(entity2);
		aaaa.add(newEntity2);
		aaaa.add(newEntity2_);

		aaaa.add(test1Dao.findById(tempId));
		tempId = newEntity2_.getId();


		// 获取视频数据
//		String 紫罗兰剧集2html = xHttpClientUtils.getHtml("https://www.bilibili.com/bangumi/play/ep173286");
		//String 紫罗兰剧集2html = restTemplate.postForObject("https://www.bilibili.com/bangumi/play/ep173286", null, String.class);
		//String 紫罗兰动画html = restTemplate.postForObject("https://bangumi.bilibili.com/anime/21542", null, String.class);

		String 一人之下动画html = restTemplate.postForObject("http://www.iqiyi.com/a_19rrhcxxf9.html#vfrm=2-4-0-1", null, String.class);
		String 声之形动画html = restTemplate.postForObject("http://www.iqiyi.com/v_19rrdx0s04.html#vfrm=2-4-0-1", null, String.class);


		List<Object> 动画html列表 = new ArrayList<>();
//		动画html列表.add(紫罗兰剧集2html);
//		动画html列表.add(紫罗兰动画html);
		动画html列表.add(一人之下动画html);
		动画html列表.add(声之形动画html);


		aaaa.add(动画html列表);

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
