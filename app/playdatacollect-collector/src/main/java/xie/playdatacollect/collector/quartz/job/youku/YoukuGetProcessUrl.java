package xie.playdatacollect.collector.quartz.job.youku;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.youku.YoukuProcessUrl;
import xie.playdatacollect.spider.webmagic.processor.youku.YoukuZhuifanPageProcessor;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class YoukuGetProcessUrl extends XBaseQuartzJobBean {

	@Resource
	private AllServiceUtil allServiceUtil;

	@Override
	public void executeJob(JobExecutionContext context) {
		runSpider();
	}

	protected void runSpider() {
		YoukuZhuifanPageProcessor pageProcessor = new YoukuZhuifanPageProcessor();
		Spider spider = Spider.create(pageProcessor).thread(2);
		List<ResultItems> resultItemsList = spider.getAll(Arrays.asList(pageProcessor.getUrl()));
		for (ResultItems resultItem : resultItemsList) {
			List<YoukuProcessUrl> list = resultItem.get("list");

			for (YoukuProcessUrl url : list) {
				try {
					// 根据页面获取其他特定信息
					allServiceUtil.getProcessUrlUtils().saveProcessUrlData(
							PlayDataConst.SOURCE_KEY_YOUKU,
							url.getTitle(),
							PlayDataConst.SOURCE_TYPE_PROGRAM,
							"",
							url.getHref(),
							new Date(),
							null
					);
				} catch (Exception e) {
					logger.error("根据页面获取其他特定信息出错", e);
				}
			}
		}
	}

}
