package xie.playdatacollect.spider.webmagic.processor.bilibili;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class BilibiliEpisodeApiProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(2).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		String baseUrl = "https://api.bilibili.com/archive_stat/stat?aid=";
		String name = page.getHtml().$(".info-content .b-head h1", "title").get();
		page.putField("名字", name);
		if (page.getResultItems().getAll().get("名字") == null || "null".equals(page.getResultItems().getAll().get("名字"))) {
			page.putField("名字", page.getHtml().$(".header-info h1", "text"));
		}

		page.putField("播放数", page.getHtml().$(".info-count-item-play em", "text"));
		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
			page.putField("播放数", page.getHtml().$(".view-count span", "text"));
		}

		page.putField("追番人数", page.getHtml().$(".info-count-item-fans em", "allText"));
		if (page.getResultItems().getAll().get("追番人数") == null || page.getResultItems().getAll().get("追番人数").toString() == null) {
			page.putField("追番人数", page.getHtml().$(".bangumi-order-wrap #v_ctimes", "text"));
		}

		page.putField("弹幕总数", page.getHtml().$(".info-count-item-review em", "text"));
		if (page.getResultItems().getAll().get("弹幕总数") == null || page.getResultItems().getAll().get("弹幕总数").toString() == null) {
			page.putField("弹幕总数", page.getHtml().$(".count-wrapper .danmu-count span", "text"));
		}


		page.putField("正在观看人数", page.getHtml().$(".bilibili-player-watching-number", "text"));
		page.putField("评论数", page.getHtml().$(".comment-title span", "text"));
		page.putField("aid", page.getHtml().$(".info-sec-av", "text"));

//		page.putField("description", page.getHtml().xpath("//div[@class='lemma-summary']/allText()"));
	}

	@Override
	public Site getSite() {
		return site;
	}

}
