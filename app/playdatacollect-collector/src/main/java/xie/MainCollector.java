package xie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.Spider;
import xie.component.httpclient.XHttpClientUtils;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.core.service.MetricService;
import xie.playdatacollect.core.service.TagService;
import xie.playdatacollect.core.service.ValueService;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliNewYear2018Processor;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Service;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Service;

import javax.annotation.Resource;
import java.util.*;

@SpringBootApplication
@RestController
//@ComponentScan("xie")
//@EntityScan("xie")
//@EnableJpaRepositories(basePackages = "xie")
public class MainCollector {

	@Resource
	RestTemplate restTemplate;
	@Resource
	XHttpClientUtils xHttpClientUtils;
	@Resource
	ProcessBilibili processBilibili;

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
	public Object getAndSaveData() {

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


//		try {
//			// 获取视频数据
//			String 紫罗兰剧集2html = xHttpClientUtils.getHtml("https://www.bilibili.com/bangumi/play/ep173286");
//			//String 紫罗兰剧集2html = restTemplate.postForObject("https://www.bilibili.com/bangumi/play/ep173286", null, String.class);
//			//String 紫罗兰动画html = restTemplate.postForObject("https://bangumi.bilibili.com/anime/21542", null, String.class);
//
//			String 一人之下动画html = restTemplate.postForObject("http://www.iqiyi.com/a_19rrhcxxf9.html#vfrm=2-4-0-1", null, String.class);
//			String 声之形动画html = restTemplate.postForObject("http://www.iqiyi.com/v_19rrdx0s04.html#vfrm=2-4-0-1", null, String.class);
//
//
//			List<Object> 动画html列表 = new ArrayList<>();
//			//	动画html列表.add(紫罗兰剧集2html);
//			//	动画html列表.add(紫罗兰动画html);
//			动画html列表.add(一人之下动画html);
//			动画html列表.add(声之形动画html);
//
//
//			aaaa.add(动画html列表);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return aaaa;
	}


	Logger logSchedule = LoggerFactory.getLogger(BilibiliAnimePageProcessor.class);

	/**
	 * 从0点开始,每2个小时执行一次
	 */
//	@Scheduled(cron = "0/10 * * * * ?")
//	@Scheduled(cron = "0 0/1 * * * ?")
	public void runScheduled() {
		System.out.println(new Date() + "----开始执行定时抓取任务");

		try {

//			tagService.insertNewKeyName("bilibili", "bilibili", TagEntity.class);
//			tagService.insertNewKeyName("bilibili" + new Date(), "bilibili" + new Date(), TagEntity.class);
//			tagService.insertNewKeyName("youku", "优酷", TagEntity.class);
//			tagService.insertNewKeyName("iqiyi", "爱奇艺", TagEntity.class);
//
//			metricService.insertNewKeyName("play", "播放量", MetricEntity.class);
//			metricService.insertNewKeyName("fans", "追番量", MetricEntity.class);
//			metricService.insertNewKeyName("review", "弹幕量", MetricEntity.class);
//			metricService.insertNewKeyName(new Date() + "", new Date() + "", MetricEntity.class);

//			System.out.println(metricService.findByKey("review"));
		} catch (Exception e) {
			e.printStackTrace();
		}


		runSpider();

		System.out.println("抓取任务 end");
	}

	private void runSpider() {
		long dateTime = System.currentTimeMillis();

		Spider spiderBLNormal = Spider.create(new BilibiliAnimePageProcessor()).thread(2);
		Spider spiderBLNY2018 = Spider.create(new BilibiliNewYear2018Processor()).thread(2);

		// multi download
		List<String> listBLNormal = new ArrayList<>();

		//XFileWriter.readList();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483


		listBLNormal.add("https://www.bilibili.com/video/av6117110"); // 【极乐净土】咬人猫/有咩酱/赤九玖❤155小分队o(*≧▽≦)ツ


		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep115339"); // 3月的狮子 第二季 第24话 混沌/隈仓
		listBLNormal.add("https://bangumi.bilibili.com/anime/21542"); // 紫罗兰动画
		listBLNormal.add("https://bangumi.bilibili.com/anime/6445"); // 3月的狮子 第二季
		listBLNormal.add("https://bangumi.bilibili.com/anime/21755"); // 刻刻
		listBLNormal.add("https://bangumi.bilibili.com/anime/21554"); // 龙王的工作
		listBLNormal.add("https://bangumi.bilibili.com/anime/21466"); // OVERLORDⅡ
		listBLNormal.add("https://bangumi.bilibili.com/anime/21719"); // pop子和pipi美的日常


		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173286"); // 紫罗兰 1
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173287"); // 紫罗兰 2
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173288"); // 紫罗兰 3
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173289"); // 紫罗兰 4
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173290"); // 紫罗兰 5
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173291"); // 紫罗兰 6
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173292"); // 紫罗兰 7


		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173248"); // OVERLORDⅡ 1
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173249"); // OVERLORDⅡ 2
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173250"); // OVERLORDⅡ 3
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173251"); // OVERLORDⅡ 4
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173252"); // OVERLORDⅡ 5
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173253"); // OVERLORDⅡ 6
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep173254"); // OVERLORDⅡ 7

		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172166"); // pop子和pipi美的日常 1
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172167"); // pop子和pipi美的日常 2
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172168"); // pop子和pipi美的日常 3
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172169"); // pop子和pipi美的日常 4
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172170"); // pop子和pipi美的日常 5
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172171"); // pop子和pipi美的日常 6
		listBLNormal.add("https://www.bilibili.com/bangumi/play/ep172172"); // pop子和pipi美的日常 7

		List<String> listBLNY2018 = new ArrayList<>();
		listBLNY2018.add("https://www.bilibili.com/blackboard/bnj2018.html");

		processBilibili.spiderGetAll(spiderBLNormal, listBLNormal, dateTime);
		processBilibili.spiderGetAll(spiderBLNY2018, listBLNY2018, dateTime);
	}

	public static void main(String[] args) {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		ConfigurableApplicationContext run = SpringApplication.run(MainCollector.class, args);
		String property = run.getEnvironment().getProperty("ds.password");
		System.out.println(property);
	}
}
