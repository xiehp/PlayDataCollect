package xie.playdatacollect.spider.webmagic.processor.iqiyi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataFactory;
import xie.playdatacollect.common.utils.PlayDataUtils;

import java.util.regex.Pattern;

public class IqiyiPlayPageProcessor implements PageProcessor {

	long time;

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	public IqiyiPlayPageProcessor(long time) {
		this.time = time;
	}

	@Override
	public void process(Page page) {
		String tvId = page.getHtml().regex("tvId:([0-9]+)").get();

		Document htmlDom = Jsoup.parse(page.getHtml().get());
		Elements name = htmlDom.body().select(".dyDs_nameBox");
		Elements playCount = htmlDom.body().select("#widget-playcount");

		page.putField("名称", name.text());
		page.putField("播放数", playCount.text());
		page.putField("播放数", playCount.text());
//		page.putField("评论数", playCount.text());

		CollectedData collectedData = new CollectedDataFactory(time).create();
		collectedData.setName(name.text());
		collectedData.setPlayCount(PlayDataUtils.parseValue(playCount.text()));
		page.putField("collectedData", collectedData);
	}

	@Override
	public Site getSite() {
		return site;
	}
}
