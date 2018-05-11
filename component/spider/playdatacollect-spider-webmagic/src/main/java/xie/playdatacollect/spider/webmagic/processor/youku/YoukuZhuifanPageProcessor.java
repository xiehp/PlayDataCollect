package xie.playdatacollect.spider.webmagic.processor.youku;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiProcessUrl;

import java.util.ArrayList;
import java.util.List;

public class YoukuZhuifanPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	public static final String URL = "http://comic.youku.com/bangumi";

	public String getUrl() {
		return URL;
	}

	@Override
	public void process(Page page) {
		Document htmlDom = Jsoup.parse(page.getHtml().get());

		Elements ykContent = htmlDom.body().select(".yk-content");
		Elements ykRow = ykContent.select(".yk-row");
		Elements infoList = ykContent.select(".info-list");
		Elements shortTitleAList = infoList.select(".short-title a");


		List<YoukuProcessUrl> listUrl = new ArrayList<>();
		shortTitleAList.forEach(element -> {
			String title = element.text();
			String url = element.attr("href");
			YoukuProcessUrl youkuProcessUrl = new YoukuProcessUrl();
			youkuProcessUrl.setHref(url);
			youkuProcessUrl.setTitle(title);
			listUrl.add(youkuProcessUrl);
		});

		page.putField("list", listUrl);
	}

	@Override
	public Site getSite() {
		return site;
	}

}
