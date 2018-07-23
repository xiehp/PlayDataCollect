package xie.playdatacollect.spider.webmagic.processor.iqiyi;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class IqiyiSearchPage_DongmanProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		Html html = page.getHtml();

		Selectable weekTab = html.$("[data-tabname=weekTab],.j_cartoon_everyday");
		Selectable detailList = weekTab.$("qy-mod-ul").$("qy-mod-li");

//		String name = html.$(".info-content .b-head h1", "title").get();
//		page.putField("名字", name);
//		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
//			page.putField("名字", html.$(".header-info h1", "text"));
//		}
//		if (page.getResultItems().getAll().get("名字") == null || page.getResultItems().getAll().get("名字").toString() == null) {
//			page.putField("名字", html.$(".v-title h1", "text"));
//		}
//
//		page.putField("播放数", html.$(".info-count-item-play em", "text"));
//		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
//			page.putField("播放数", html.$(".view-count span", "text"));
//		}
//		if (page.getResultItems().getAll().get("播放数") == null || page.getResultItems().getAll().get("播放数").toString() == null) {
//			page.putField("播放数", html.$(".v-title-line #dianji", "text"));
//		}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
