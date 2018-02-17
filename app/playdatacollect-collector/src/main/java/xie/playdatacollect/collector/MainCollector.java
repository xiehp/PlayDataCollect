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
import xie.common.json.XJsonUtil;
import xie.common.string.XStringUtils;
import xie.module.httpclient.XHttpClientUtils;
import xie.playdatacollect.core.service.MetricService;
import xie.playdatacollect.core.service.TagService;
import xie.playdatacollect.core.service.ValueService;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test1.Test1Service;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Entity;
import xie.playdatacollect.testandstudy.db.app.fun.test1.Test2.Test2Service;

import javax.annotation.Resource;
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


	Logger logSchedule = LoggerFactory.getLogger(BilibiliAnimePageProcessor.class);

	/**
	 * 从0点开始,每2个小时执行一次
	 */
//	@Scheduled(cron = "0/10 * * * * ?")
	@Scheduled(cron = "0 0/1 * * * ?")
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

		spiderGetAll(spiderBLNormal, listBLNormal, dateTime);
		spiderGetAll(spiderBLNY2018, listBLNY2018, dateTime);
	}

	private void spiderGetAll(Spider spider, List<String> list, long dateTime) {

		Logger logSpider = LoggerFactory.getLogger(Spider.class);
		List<ResultItems> resultItemses = spider.getAll(list);

		InfluxDB influxDB = InfluxDBFactory.connect("http://linux2.acgimage.cn:48086");
		influxDB.setDatabase("play_data");

		List<String> aidList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		List<String> siteList = new ArrayList<>();
		List<Integer> 追番人数List = new ArrayList<>();
		List<Integer> 承包数List = new ArrayList<>();
		while (true) {
			for (ResultItems resultItemse : resultItemses) {
				try {
					logSpider.info(resultItemse.getAll().toString());

					String 名字 = resultItemse.getAll().get("名字").toString();
//				int 播放数 = 0;
//				int 追番人数 = 0;
//				int 弹幕总数 = 0;
					Object aid = resultItemse.getAll().get("aid");
					if (aid != null && aid.toString() != null) {
						aid = aid.toString().toLowerCase().replace("av", "");
						if (!aid.toString().matches("\\d+")) {
							aid = "";
						}
					} else {
						aid = "";
					}

					if (XStringUtils.isNotBlank(aid.toString())) {
						siteList.add("bilibili");
						nameList.add(名字);
						aidList.add(aid.toString());
						追番人数List.add(parseValue(resultItemse.getAll().get("追番人数")));
						承包数List.add(parseValue(resultItemse.getAll().get("承包数")));
					} else {
						Point.Builder builder = Point.measurement("base_data");
						builder.tag("网站", "bilibili");
						builder.tag("名字", 名字);
//						builder.addField("追番人数", 追番人数);
//						builder.addField("弹幕总数", 弹幕总数);

						builder.addField("播放数", parseValue(resultItemse.getAll().get("播放数")));
						builder.addField("分享数", parseValue(resultItemse.getAll().get("分享数")));
						builder.addField("收藏数", parseValue(resultItemse.getAll().get("收藏数")));
						builder.addField("硬币数", parseValue(resultItemse.getAll().get("硬币数")));
						builder.addField("承包数", parseValue(resultItemse.getAll().get("承包数")));
						builder.addField("评论数", parseValue(resultItemse.getAll().get("评论数")));
						builder.time(dateTime, TimeUnit.MILLISECONDS);
						Point point = builder.build();
						influxDB.write(point);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

//				resultItemse.getAll().forEach((key, value) -> {
//					ValueEntity valueEntity = new ValueEntity();
//					valueEntity.setTag(key);
//					valueEntity.setTime(new Date());
//					valueEntity.setValue(value == null ? null : value.toString());
//					valueEntity = valueService.save(valueEntity);
//					//System.out.println(valueEntity.toMapWithOutBase());
//
//
//					if ("播放数".equals(key) || "追番人数".equals(key) || "弹幕总数".equals(key)) {
//						value = parseValue(value);
//						//String requestObject = "cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000";
//						String requestObject = key + ",名字=" + 名字 + ",网站=bilibili value=" + value + " " + dateTime + "";
//						//String result = restTemplate.postForObject("http://linux2.acgimage.cn:48086/write?db=play_data", requestObject, String.class);
//						//log.info(result);
//					}
//				});

			}

			break;
		}


		// 处理aid
		String baseApiUrl = "http://api.bilibili.com/archive_stat/stat?aid=";
		for (int i = 0; i < aidList.size(); i++) {
			try {
				String result = restTemplate.getForObject(baseApiUrl + aidList.get(i), String.class);
				Map<String, Object> map = XJsonUtil.fromJsonString(result);
				map = (Map<String, Object>) map.get("data");
				int aid = (int) map.get("aid");
				int 播放数 = (int) map.get("view");
				int 弹幕总数 = (int) map.get("danmaku");
				int 评论数 = (int) map.get("reply");
				int 收藏数 = (int) map.get("favorite");
				int 硬币数 = (int) map.get("coin");
				int 分享数 = (int) map.get("share");
				int 喜欢数 = (int) map.get("like");
				int 当前排名 = (int) map.get("now_rank");
				int 历史排名 = (int) map.get("his_rank");

				int 追番人数 = 追番人数List.get(i);
				int 承包数 = 承包数List.get(i);


				Point point = Point.measurement("base_data")
						.tag("网站", siteList.get(i))
						.tag("名字", nameList.get(i))
						.tag("aid", String.valueOf(aid))
						.addField("播放数", 播放数)
						.addField("弹幕总数", 弹幕总数)
						.addField("评论数", 评论数)
						.addField("收藏数", 收藏数)
						.addField("硬币数", 硬币数)
						.addField("分享数", 分享数)
						.addField("喜欢数", 喜欢数)
						.addField("当前排名", 当前排名)
						.addField("历史排名", 历史排名)
						.addField("追番人数", 追番人数)
						.addField("承包数", 承包数)
						.time(dateTime, TimeUnit.MILLISECONDS)
						.build();
				influxDB.write(point);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		influxDB.close();
		spider.close();
	}


	private int parseValue(Object value) {
		int result = 0;

		if (value == null || value.toString() == null) {
			return result;
		}

		if (XStringUtils.isBlank(value.toString())) {
			return result;
		}

		if (value.toString().contains("万")) {
			value = value.toString().replace("万", "");
			result = (int) (Double.valueOf(value.toString()) * 10000);
		} else if (value.toString().contains("AV") || value.toString().contains("av")) {
			value = value.toString().replace("AV", "");
			value = value.toString().replace("av", "");
			result = Integer.valueOf(value.toString());
		} else {
			result = Integer.valueOf(value.toString());
		}

		return result;
	}

	public static void main(String[] args) {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(MainCollector.class, args);
	}
}
