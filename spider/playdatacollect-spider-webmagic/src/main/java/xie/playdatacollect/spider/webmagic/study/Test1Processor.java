package xie.playdatacollect.spider.webmagic.study;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * info:简书首页爬虫
 *
 * @author shang
 * @date 16/9/9
 *
 */
public class Test1Processor implements PageProcessor {

	private Site site = Site.me()
			.setDomain("jianshu.com")
			.setSleepTime(100)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
	;

	public static final String list = "http://www.jianshu.com";

	@Override
	public void process(Page page) {
		if (page.getUrl().regex(list).match()) {
			List<Selectable> list = page.getHtml().xpath("//ul[@class='article-list thumbnails']/li").nodes();
			for (Selectable s : list) {
				String title = s.xpath("//div/h4/a/text()").toString();
				String link = s.xpath("//div/h4").links().toString();
				News news = new News();
				news.setTitle(title);
				news.setInfo(title);
				news.setLink(link);
				page.putField("news" + title, news);
			}
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new Test1Processor());
		spider.addUrl("http://www.jianshu.com");
		spider.addPipeline(new NewsPipeline());
		spider.thread(5);
		spider.setExitWhenComplete(true);
		spider.start();
	}
}
