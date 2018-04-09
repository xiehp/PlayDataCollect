package xie.playdatacollect.collector.quartz.job.iqiyi;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.core.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiDongmanPageProcessor;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiProcessUrl;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class IQiYiGetProcessUrl extends XBaseQuartzJobBean {

	@Resource
	private AllDaoUtil allDaoUtil;
	@Resource
	private AllServiceUtil allServiceUtil;

	@Override
	protected void executeJob(JobExecutionContext context) {
		runSpider();
	}

	protected void runSpider() {
		long dateTime = System.currentTimeMillis();

		Spider spider = Spider.create(new IqiyiDongmanPageProcessor()).thread(2);
		List<ResultItems> resultItemsList = spider.getAll(Arrays.asList("http://www.iqiyi.com/dongman"));
		for (ResultItems resultItem : resultItemsList) {
			List<IqiyiProcessUrl> list = resultItem.get("list");

			for (IqiyiProcessUrl url : list) {

				allServiceUtil.getProcessUrlService().saveProcessUrlData(
						PlayDataConst.SOURCE_KEY_IQIYI,
						url.getTitle(),
						PlayDataConst.SOURCE_TYPE_PROGRAM,
						"",
						url.getHref(),
						new Date()
				);
			}
		}
	}

}
