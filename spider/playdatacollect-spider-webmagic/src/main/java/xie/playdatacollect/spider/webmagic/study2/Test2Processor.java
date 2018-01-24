package xie.playdatacollect.spider.webmagic.study2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * info:简书首页爬虫
 *
 * @author shang
 * @date 16/9/9
 */
public class Test2Processor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(2).setSleepTime(500).setUseGzip(true);

	@Override
	public void process(Page page) {
		String name = page.getHtml().$(".info-content .b-head h1", "title").get();
		page.putField("名字", name);
		if (page.getResultItems().getAll().get("名字") == null) {
			page.putField("名字", page.getHtml().$(".header-info h1", "text"));
		}
		page.putField("播放数", page.getHtml().$(".info-count-item-play em", "text"));
		page.putField("追番人数", page.getHtml().$(".info-count-item-fans em", "allText"));
		page.putField("弹幕总数", page.getHtml().$(".info-count-item-review em", "text"));
//		page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new Test2Processor()).thread(1);

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

		Logger log = LoggerFactory.getLogger(Test2Processor.class);
		List<ResultItems> resultItemses = spider.getAll(list);
		for (ResultItems resultItemse : resultItemses) {
			log.info(resultItemse.getAll().toString());
		}
		spider.close();
	}
}
