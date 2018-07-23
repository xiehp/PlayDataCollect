package xie.playdatacollect.spider.webmagic.processor.bilibili;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class BilibiliNewYear2018Processor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		Html html = page.getHtml();

		page.putField("名字", "bilibili拜年祭2018");

		page.putField("播放数", html.$(".ny2018-actions .ny2018-actions-view p:nth-child(2)", "text"));

//		page.putField("追番人数", html.$(".info-count-item-fans em", "allText"));

//		page.putField("弹幕总数", html.$(".info-count-item-review em", "text"));

//		page.putField("正在观看人数", html.$(".bilibili-player-watching-number", "text"));
		page.putField("aid", 17027625);
		page.putField("分享数", html.$(".ny2018-actions-share .ny2018-actions-text p:nth-child(2)", "text"));
		page.putField("收藏数", html.$(".ny2018-actions-collect .ny2018-actions-text p:nth-child(2)", "text"));
		page.putField("硬币数", html.$(".ny2018-actions-pay .ny2018-actions-text p:nth-child(2)", "text"));
		page.putField("承包数", html.$(".ny2018-charge .ny2018-charge-content .ny2018-charge-view-content p:nth-child(2)", "text"));
		page.putField("评论数", html.$(".ny2018-comment-comment .b-head span:nth-child(1)", "text"));

//		page.putField("description", html.xpath("//div[@class='lemma-summary']/allText()"));
	}

	@Override
	public Site getSite() {
		return site;
	}

}
