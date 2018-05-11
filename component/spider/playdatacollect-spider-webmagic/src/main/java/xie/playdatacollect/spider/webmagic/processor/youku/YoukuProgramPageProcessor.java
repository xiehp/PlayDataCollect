package xie.playdatacollect.spider.webmagic.processor.youku;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.common.utils.utils.XRegExpUtils;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataFactory;
import xie.playdatacollect.common.utils.PlayDataUtils;

import java.util.ArrayList;
import java.util.List;

public class YoukuProgramPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	private CollectedDataFactory collectedDataFactory;

	public YoukuProgramPageProcessor(long time) {
		collectedDataFactory = new CollectedDataFactory(time);
	}

	@Override
	public void process(Page page) {

		Document htmlDom = Jsoup.parse(page.getHtml().get());

		Elements ykContent = htmlDom.body().select(".yk-content");
		Elements pScore = ykContent.select(".p-score");

		String htmlStr =page.getHtml().get();
		String playCountStr = XRegExpUtils.findOnceAndFirstGroup(htmlStr, "(总播放数.*?)</li>");
		String commentCountStr = XRegExpUtils.findOnceAndFirstGroup(htmlStr, "(评论.*?)</li>");
		String likeCountStr = XRegExpUtils.findOnceAndFirstGroup(htmlStr, "(顶.*?)</li>");
		String scorceStr = pScore.text();
		Long playCount = PlayDataUtils.parseValue(playCountStr);
		Integer commentCount = PlayDataUtils.parseIntegerValue(commentCountStr);
		Integer likeCount = PlayDataUtils.parseIntegerValue(likeCountStr);
		Double scorce = PlayDataUtils.parseDoubleValue(scorceStr);

		CollectedData collectedData = collectedDataFactory.create();
		collectedData.setPlayCount(playCount);
		collectedData.setCommentCount(commentCount);
		collectedData.setLikeCount(likeCount);
		collectedData.setScore(scorce);

		page.putField("collectedData", collectedData);
	}

	@Override
	public Site getSite() {
		return site;
	}

}
