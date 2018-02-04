package xie.playdatacollect.collector;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.module.httpclient.XHttpClientUtils;
import xie.playdatacollect.core.entity.MetricEntity;
import xie.playdatacollect.core.entity.TagEntity;
import xie.playdatacollect.core.entity.ValueEntity;
import xie.playdatacollect.core.service.MetricService;
import xie.playdatacollect.core.service.TagService;
import xie.playdatacollect.core.service.ValueService;
import xie.playdatacollect.spider.webmagic.study2.BilibiliAnimePageProcessor;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Service;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
	@Scheduled(cron = "0/30 * * * * ?")
	public void runScheduled() {
		System.out.println(new Date() + "----开始执行定时抓取任务");

		try {

			tagService.insertNewKeyName("bilibili", "bilibili", TagEntity.class);
			tagService.insertNewKeyName("bilibili" + new Date(), "bilibili" + new Date(), TagEntity.class);
			tagService.insertNewKeyName("youku", "优酷", TagEntity.class);
			tagService.insertNewKeyName("iqiyi", "爱奇艺", TagEntity.class);

			metricService.insertNewKeyName("play", "播放量", MetricEntity.class);
			metricService.insertNewKeyName("fans", "追番量", MetricEntity.class);
			metricService.insertNewKeyName("review", "弹幕量", MetricEntity.class);
			metricService.insertNewKeyName(new Date() + "", new Date() + "", MetricEntity.class);

			System.out.println(metricService.findByKey("review"));
			System.out.println("MyCommandLineRunner1 end");
		} catch (Exception e) {
			e.printStackTrace();
		}


		runSpider();
	}

	private void runSpider() {
		Spider spider = Spider.create(new BilibiliAnimePageProcessor()).thread(1);

		// multi download
		List<String> list = new ArrayList<>();

		list.add("https://www.bilibili.com/bangumi/play/ep173286"); // 紫罗兰剧集2
		list.add("https://bangumi.bilibili.com/anime/21542"); // 紫罗兰动画
		list.add("https://bangumi.bilibili.com/anime/6445"); // 3月的狮子 第二季
		list.add("https://www.bilibili.com/bangumi/play/ep115339"); // 3月的狮子 第二季 第24话 混沌/隈仓

		long dateTime = System.currentTimeMillis();
		Logger log = LoggerFactory.getLogger(BilibiliAnimePageProcessor.class);
		Logger logSpider = LoggerFactory.getLogger(Spider.class);
		List<ResultItems> resultItemses = spider.getAll(list);
		InfluxDB influxDB = InfluxDBFactory.connect("http://172.17.0.2:8086", "root", "root");
		while (true) {
			for (ResultItems resultItemse : resultItemses) {
				log.info(resultItemse.getAll().toString());

				String 名字 = resultItemse.getAll().get("名字").toString();
				int 播放数 = 0;
				int 追番人数 = 0;
				int 弹幕总数 = 0;
				resultItemse.getAll().forEach((key, value) -> {
					ValueEntity valueEntity = new ValueEntity();
					valueEntity.setTag(key);
					valueEntity.setTime(new Date());
					valueEntity.setValue(value == null ? null : value.toString());
					valueEntity = valueService.save(valueEntity);
					//System.out.println(valueEntity.toMapWithOutBase());


					if ("播放数".equals(key) || "追番人数".equals(key) || "弹幕总数".equals(key)) {
						try {
							if (value != null && value.toString().contains("万")) {
								value = value.toString().replace("万", "");
								value = (int)(Double.valueOf(value.toString()) * 10000);
							}
							//String requestObject = "cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000";
							String requestObject = key + ",名字=" + 名字 + ",网站=bilibili value=" + value + " " + dateTime + "";
							String result = restTemplate.postForObject("http://linux2.acgimage.cn:48086/write?db=play_data", requestObject, String.class);

							influxDB.write(Point.measurement(key)
									.time(dateTime, TimeUnit.MILLISECONDS)
									.addField("网站", "bilibili")
									.addField("名字", 名字)
									.addField("播放数", 播放数)
									.addField("追番人数", 追番人数)
									.addField("弹幕总数", 弹幕总数)
									.build());
							influxDB.write("cpu,atag=test1 idle=100,usertime=10,system=1");

							log.info(result);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

			break;
		}
		influxDB.close();
		spider.close();
	}

	public static void main(String[] args) {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(MainCollector.class, args);
	}
}
