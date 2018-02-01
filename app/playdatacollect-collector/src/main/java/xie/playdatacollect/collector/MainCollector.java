package xie.playdatacollect.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xie.module.httpclient.XHttpClientUtils;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Service;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@ComponentScan("xie")
@EntityScan("xie")
@EnableJpaRepositories(basePackages = "xie")
public class MainCollector {

	@Resource
	RestTemplate restTemplate;
	@Resource
	XHttpClientUtils xHttpClientUtils;

	@Resource
	Test1Service test1Service;
	@Resource
	Test2Service test2Service;

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
		Test1Entity newEntity = test1Service.save(entity);
		Test1Entity newEntity_ = test1Service.findById(newEntity.getId());


		Test2Entity entity2 = new Test2Entity();
		entity2.setCol2(aaa.get("title").toString());
		entity2.setCol1(aaa.get("payCount").toString());
		Test2Entity newEntity2 = test2Service.save(entity2);
		Test2Entity newEntity2_ = test2Service.findById(newEntity2.getId());

		List<Object> aaaa = new ArrayList<>();
		aaaa.add(aaa);
		aaaa.add(entity);
		aaaa.add(newEntity);
		aaaa.add(newEntity_);
		aaaa.add(entity2);
		aaaa.add(newEntity2);
		aaaa.add(newEntity2_);

		aaaa.add(test1Service.findById(tempId));
		tempId = newEntity2_.getId();


		try {
			// 获取视频数据
			String 紫罗兰剧集2html = xHttpClientUtils.getHtml("https://www.bilibili.com/bangumi/play/ep173286");
			//String 紫罗兰剧集2html = restTemplate.postForObject("https://www.bilibili.com/bangumi/play/ep173286", null, String.class);
			//String 紫罗兰动画html = restTemplate.postForObject("https://bangumi.bilibili.com/anime/21542", null, String.class);

			String 一人之下动画html = restTemplate.postForObject("http://www.iqiyi.com/a_19rrhcxxf9.html#vfrm=2-4-0-1", null, String.class);
			String 声之形动画html = restTemplate.postForObject("http://www.iqiyi.com/v_19rrdx0s04.html#vfrm=2-4-0-1", null, String.class);


			List<Object> 动画html列表 = new ArrayList<>();
			//	动画html列表.add(紫罗兰剧集2html);
			//	动画html列表.add(紫罗兰动画html);
			动画html列表.add(一人之下动画html);
			动画html列表.add(声之形动画html);


			aaaa.add(动画html列表);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aaaa;
	}


	/**
	 * 从0点开始,每2个小时执行一次
	 */
	@Scheduled(cron = "0 0 0 1 * ? ")
	public void runScheduled() {
		System.out.println("----开始执行简书定时任务");
	}

	public static void main(String[] args) {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(MainCollector.class, args);
	}
}
