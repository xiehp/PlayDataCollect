package xie.playdatacollect.spider.webmagic.processor.iqiyi;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.selector.Selectable;

public class IqiyiDongmanPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(2).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		Selectable weekTab = page.getHtml().$("[data-tabname=weekTab],.j_cartoon_everyday");
		Selectable detailList = weekTab.$("qy-mod-ul").$("qy-mod-li");

		PlainText titles = (PlainText)detailList.$("link-txt", "title");

		page.putField("名字", XXX);
		page.putField("url", XXX);
		page.putField("名字", XXX);
		page.putField("名字", XXX);
//		String name = page.getHtml().$(".info-content .b-head h1", "title").get();
//		page.putField("名字", name);
//		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
//			page.putField("名字", page.getHtml().$(".header-info h1", "text"));
//		}
//		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
//			page.putField("名字", page.getHtml().$(".v-title h1", "text"));
//		}
//
//		page.putField("播放数", page.getHtml().$(".info-count-item-play em", "text"));
//		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
//			page.putField("播放数", page.getHtml().$(".view-count span", "text"));
//		}
//		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
//			page.putField("播放数", page.getHtml().$(".v-title-line #dianji", "text"));
//		}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
