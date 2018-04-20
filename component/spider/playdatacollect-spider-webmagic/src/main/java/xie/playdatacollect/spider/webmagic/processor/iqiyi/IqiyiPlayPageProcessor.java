package xie.playdatacollect.spider.webmagic.processor.iqiyi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class IqiyiPlayPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	@Override
	public void process(Page page) {
		Document htmlDom = Jsoup.parse(page.getHtml().get());
		Elements name = htmlDom.body().select(".dyDs_nameBox");
		Elements playCount = htmlDom.body().select("#widget-playcount");


		page.putField("名称", name.text());
		page.putField("播放数", playCount.text());
//		page.putField("评论数", playCount.text());
	}

	@Override
	public Site getSite() {
		return site;
	}


	public static void main(String[] args) {

		Spider spider = Spider.create(new IqiyiPlayPageProcessor()).thread(3);
	}
}
