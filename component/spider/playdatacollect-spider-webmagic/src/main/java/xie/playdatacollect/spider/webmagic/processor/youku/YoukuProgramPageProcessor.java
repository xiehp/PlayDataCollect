package xie.playdatacollect.spider.webmagic.processor.youku;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.common.utils.utils.XRegExpUtils;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataFactory;
import xie.playdatacollect.common.utils.PlayDataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xie
 */
public class YoukuProgramPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(300).setUseGzip(true);

	private CollectedDataFactory collectedDataFactory;

	private long time;

	private Map<String, String> url2NameMap ;

	public YoukuProgramPageProcessor(long time, Map<String, String> url2NameMap) {
		collectedDataFactory = new CollectedDataFactory(time, PlayDataConst.SOURCE_KEY_YOUKU, PlayDataConst.SOURCE_TYPE_PROGRAM);
		this.time = time;
		this.url2NameMap = url2NameMap;
	}

	@Override
	public void process(Page page) {

		String url = page.getUrl().get();

		Document htmlDom = Jsoup.parse(page.getHtml().get());

		Elements ykContent = htmlDom.body().select(".yk-content");
		Elements pScore = ykContent.select(".p-score");

		String htmlStr =page.getHtml().get();
		String playCountStr = XRegExpUtils.findOnceAndFirstGroup(htmlStr, "(总播放数.*?)</li>");
		String commentCountStr = XRegExpUtils.findOnceAndFirstGroup(htmlStr, "(评论.*?)</li>");
		String likeCountStr = XRegExpUtils.findOnceAndFirstGroup(htmlStr, "(顶.*?)</li>");
		String scoreStr = pScore.text();
		Long playCount = PlayDataUtils.parseValue(playCountStr);
		Integer commentCount = PlayDataUtils.parseIntegerValue(commentCountStr);
		Integer likeCount = PlayDataUtils.parseIntegerValue(likeCountStr);
		Double score = PlayDataUtils.parseDoubleValue(scoreStr);

		CollectedData collectedData = collectedDataFactory.create();
		collectedData.setName(url2NameMap.get(url));
		collectedData.setPlayCount(playCount);
		collectedData.setCommentCount(commentCount);
		collectedData.setLikeCount(likeCount);
		collectedData.setScore(score);

		page.putField("collectedData", collectedData);
	}

	@Override
	public Site getSite() {
		return site;
	}

}
