package xie.playdatacollect.testandstudy.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.core.entity.MetricEntity;
import xie.playdatacollect.core.entity.TagEntity;
import xie.playdatacollect.core.entity.ValueEntity;
import xie.playdatacollect.core.service.MetricService;
import xie.playdatacollect.core.service.TagService;
import xie.playdatacollect.core.service.ValueService;
import xie.playdatacollect.spider.webmagic.study2.BilibiliAnimePageProcessor;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用于初始化一些基础数据
 */
@Configuration
public class MyCommandLineRunner1 implements CommandLineRunner {

	@Resource
	TagService tagService;
	@Resource
	MetricService metricService;
	@Resource
	ValueService valueService;

	@Override
	public void run(String... args) {
		try {
			System.out.println("MyCommandLineRunner1 start");

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

		//multidownload
		String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
		List<String> list = new ArrayList<>();

		//String 紫罗兰剧集2html = xHttpClientUtils.getHtml("https://www.bilibili.com/bangumi/play/ep173286");
		//String 紫罗兰剧集2html = restTemplate.postForObject("https://www.bilibili.com/bangumi/play/ep173286", null, String.class);
		//String 紫罗兰动画html = restTemplate.postForObject("https://bangumi.bilibili.com/anime/21542", null, String.class);

		list.add("https://www.bilibili.com/bangumi/play/ep173286"); // 紫罗兰剧集2
		list.add("https://www.bilibili.com/bangumi/play/ep173286"); // 紫罗兰剧集2
		list.add("https://bangumi.bilibili.com/anime/21542"); // 紫罗兰动画
		list.add("https://bangumi.bilibili.com/anime/6445"); // 3月的狮子 第二季
		list.add("https://www.bilibili.com/bangumi/play/ep115339"); // 3月的狮子 第二季 第24话 混沌/隈仓

		Logger log = LoggerFactory.getLogger(BilibiliAnimePageProcessor.class);
		Logger logSpider = LoggerFactory.getLogger(Spider.class);
		List<ResultItems> resultItemses = spider.getAll(list);
		while (true) {
			for (ResultItems resultItemse : resultItemses) {
				log.info(resultItemse.getAll().toString());

				resultItemse.getAll().forEach((key, value) -> {
					ValueEntity valueEntity = new ValueEntity();
					valueEntity.setTag(key);
					valueEntity.setTime(new Date());
					valueEntity.setValue(value == null ? null : value.toString());
					System.out.println(valueEntity.toMapWithOutBase());
					valueEntity = valueService.save(valueEntity);
					System.out.println(valueEntity.toMapWithOutBase());
				});
			}


			log.warn("warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test ");
			log.debug("debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test ");
			log.error("error test error test error test error test error test error test error test error test error test error test error test ");
			logSpider.warn("warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test warn test ");
			logSpider.debug("debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test debug test ");
			logSpider.error("error test error test error test error test error test error test error test error test error test error test error test ");

			break;
		}
		//spider.close();
	}
}