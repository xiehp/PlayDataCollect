package xie.playdatacollect.spider.webmagic.processor.bilibili;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Map;

public class BilibiliAnimePageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	Map<String, String> url2NameMap;

	public BilibiliAnimePageProcessor(Map<String, String> url2NameMap) {
		this.url2NameMap = url2NameMap;
	}

	@Override
	public void process(Page page) {
		Html html = page.getHtml();

		// 节目ID数据
		String seasonId = html.regex("\"season_id.*?(\\d*?),\"").get();
		if (seasonId != null) {
			page.putField("seasonId", seasonId);
		}

		// 剧集ID数据
		page.putField("aid", html.$(".info-sec-av", "text"));
		if (page.getResultItems().getAll().get("aid") == null || page.getResultItems().getAll().get("aid").toString() == null) {
			page.putField("aid", page.getRequest().getUrl().replace("https://www.bilibili.com/video/", "").replace("/", ""));
		}

		String name = html.$(".info-content .b-head h1", "title").get();
		page.putField("名字", name);
		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
			page.putField("名字", html.$(".header-info h1", "text"));
		}
		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
			page.putField("名字", html.$(".v-title h1", "text"));
		}
		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
			page.putField("名字", html.$(".media-info-title-t", "text"));
		}

		page.putField("播放数", html.$(".info-count-item-play em", "text"));
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", html.$(".view-count span", "text"));
		}
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", html.$(".v-title-line #dianji", "text"));
		}
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", html.$(".media-info-count-item-play em", "text"));
		}


		page.putField("追番人数", html.$(".info-count-item-fans em", "allText"));
		if (page.getResultItems().getAll().get("追番人数") == null || page.getResultItems().getAll().get("追番人数").toString() == null) {
			page.putField("追番人数", html.$(".bangumi-order-wrap #v_ctimes", "text"));
		}
		if (page.getResultItems().getAll().get("追番人数") == null || page.getResultItems().getAll().get("追番人数").toString() == null) {
			page.putField("追番人数", html.$(".media-info-count-item-fans em", "text"));
		}

		page.putField("弹幕总数", html.$(".info-count-item-review em", "text"));
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", html.$(".count-wrapper .danmu-count span", "text"));
		}
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", html.$(".v-title-line #dm_count", "text"));
		}
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", html.$(".media-info-count-item-review em", "text"));
		}


		page.putField("承包数", html.$(".sponsor-tosponsor-img span", "text"));
		if (page.getResultItems().getAll().get("承包数") == null || page.getResultItems().getAll().get("承包数").toString() == null) {
			page.putField("承包数", html.$(".btn-sponsor-wrapper .sponsor-count span", "text"));
		}
		if (page.getResultItems().getAll().get("承包数") == null || page.getResultItems().getAll().get("承包数").toString() == null) {
			page.putField("承包数", html.$(".elecrank-header .title span:nth-child(2)", "text"));
		}

		page.putField("正在观看人数", html.$(".bilibili-player-watching-number", "text"));

		page.putField("评论数", html.$(".comment-title span", "text"));
		if (page.getResultItems().getAll().get("评论数") == null || page.getResultItems().getAll().get("评论数").toString() == null) {
			page.putField("评论数", html.$(".media-tab-wrp .media-tab-nav li:nth-child(3)", "text"));
		}


		page.putField("评分", html.$(".media-info-score-content div:nth-child(1)", "text"));

		page.putField("评分人数", html.$(".media-info-score-content div:nth-child(2)", "text"));


		page.putField("url", page.getUrl());
		if (url2NameMap != null) {
			page.putField("influxdbName", url2NameMap.get(page.getUrl()));
		}

//		page.putField("description", html.xpath("//div[@class='lemma-summary']/allText()"));
	}

	@Override
	public Site getSite() {
		return site;
	}

}
