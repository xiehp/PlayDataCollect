package xie.playdatacollect.spider.webmagic.processor.bilibili;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class BilibiliAnimePageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		String name = page.getHtml().$(".info-content .b-head h1", "title").get();
		page.putField("名字", name);
		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
			page.putField("名字", page.getHtml().$(".header-info h1", "text"));
		}
		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
			page.putField("名字", page.getHtml().$(".v-title h1", "text"));
		}
		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
			page.putField("名字", page.getHtml().$(".media-info-title-t", "text"));
		}

		page.putField("播放数", page.getHtml().$(".info-count-item-play em", "text"));
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", page.getHtml().$(".view-count span", "text"));
		}
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", page.getHtml().$(".v-title-line #dianji", "text"));
		}
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", page.getHtml().$(".media-info-count-item-play em", "text"));
		}



		page.putField("追番人数", page.getHtml().$(".info-count-item-fans em", "allText"));
		if (page.getResultItems().getAll().get("追番人数") == null || page.getResultItems().getAll().get("追番人数").toString() == null) {
			page.putField("追番人数", page.getHtml().$(".bangumi-order-wrap #v_ctimes", "text"));
		}
		if (page.getResultItems().getAll().get("追番人数") == null || page.getResultItems().getAll().get("追番人数").toString() == null) {
			page.putField("追番人数", page.getHtml().$(".media-info-count-item-fans em", "text"));
		}

		page.putField("弹幕总数", page.getHtml().$(".info-count-item-review em", "text"));
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", page.getHtml().$(".count-wrapper .danmu-count span", "text"));
		}
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", page.getHtml().$(".v-title-line #dm_count", "text"));
		}
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", page.getHtml().$(".media-info-count-item-review em", "text"));
		}





		page.putField("承包数", page.getHtml().$(".sponsor-tosponsor-img span", "text"));
		if (page.getResultItems().getAll().get("承包数") == null || page.getResultItems().getAll().get("承包数").toString() == null) {
			page.putField("承包数", page.getHtml().$(".btn-sponsor-wrapper .sponsor-count span", "text"));
		}
		if (page.getResultItems().getAll().get("承包数") == null || page.getResultItems().getAll().get("承包数").toString() == null) {
			page.putField("承包数", page.getHtml().$(".elecrank-header .title span:nth-child(2)", "text"));
		}

		page.putField("正在观看人数", page.getHtml().$(".bilibili-player-watching-number", "text"));

		page.putField("评论数", page.getHtml().$(".comment-title span", "text"));
		if (page.getResultItems().getAll().get("评论数") == null || page.getResultItems().getAll().get("评论数").toString() == null) {
			page.putField("评论数", page.getHtml().$(".media-tab-wrp .media-tab-nav li:nth-child(3)", "text"));
		}

		page.putField("aid", page.getHtml().$(".info-sec-av", "text"));
		if (page.getResultItems().getAll().get("aid") == null || page.getResultItems().getAll().get("aid").toString() == null) {
			page.putField("aid", page.getRequest().getUrl().replace("https://www.bilibili.com/video/","").replace("/",""));
		}


		page.putField("评分", page.getHtml().$(".media-info-score-content div:nth-child(1)", "text"));

		page.putField("评分人数", page.getHtml().$(".media-info-score-content div:nth-child(2)", "text"));


//		page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
	}

	@Override
	public Site getSite() {
		return site;
	}

}
