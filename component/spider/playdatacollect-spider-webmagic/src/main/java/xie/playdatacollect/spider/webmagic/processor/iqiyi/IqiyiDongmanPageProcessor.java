package xie.playdatacollect.spider.webmagic.processor.iqiyi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

public class IqiyiDongmanPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		Html html = page.getHtml();

//		Selectable weekTab = page.getHtml().$("[data-tabname=weekTab],.j_cartoon_everyday");
//		Selectable detailList = weekTab.$("qy-mod-ul").$("qy-mod-li");

		Document htmlDom = Jsoup.parse(html.get());
		Elements weekTab = htmlDom.body().select("[data-tabname=weekTab],.j_cartoon_everyday");
		Elements detailList = weekTab.select(".qy-mod-ul").select(".qy-mod-li");
		Elements titleWrapList = weekTab.select(".title-wrap");

		Elements linkTxtList = detailList.select(".link-txt");

		List<IqiyiProcessUrl> list = new ArrayList<>();
		for (int i = 0; i < linkTxtList.size(); i++) {
			Element linkTxt = linkTxtList.get(i);
			Attributes attributes =linkTxt.attributes();
			String title = attributes.get("title");
			String url = attributes.get("href");
			IqiyiProcessUrl iqiyiProcessUrl = new IqiyiProcessUrl();
			iqiyiProcessUrl.setHref(url);
			iqiyiProcessUrl.setTitle(title);
			list.add(iqiyiProcessUrl);
		}

		page.putField("list", list);
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
