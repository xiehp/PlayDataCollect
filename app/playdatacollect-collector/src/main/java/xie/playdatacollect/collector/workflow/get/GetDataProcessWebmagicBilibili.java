package xie.playdatacollect.collector.workflow.get;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.playdatacollect.collector.process.ProcessBilibiliByCollectedData;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class GetDataProcessWebmagicBilibili extends GetDataProcessBaseWebmagic {

	@Resource
	ProcessBilibiliByCollectedData processBilibiliByCollectedData;

	@Override
	public List<CollectedData> spiderGetAll(Spider spider, List<String> listUrl) {
		return processBilibiliByCollectedData.spiderGetAll(spider, listUrl);
	}

	@Override
	public PageProcessor getPageProcessor(Map<String, String> url2NameMap) {
		return new BilibiliAnimePageProcessor(url2NameMap);
	}

}
