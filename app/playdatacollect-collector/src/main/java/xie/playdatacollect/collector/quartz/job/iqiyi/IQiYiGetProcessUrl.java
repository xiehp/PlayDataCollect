package xie.playdatacollect.collector.quartz.job.iqiyi;

import org.quartz.JobExecutionContext;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.core.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiDongmanPageProcessor;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public abstract class IQiYiGetProcessUrl extends XBaseQuartzJobBean {

	@Resource
	AllDaoUtil allDaoUtil;
	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	ProcessBilibili processBilibili;

	@Override
	protected void executeJob(JobExecutionContext context) {
		runSpider();
	}

	private void runSpider() {
		long dateTime = System.currentTimeMillis();

		Spider spider = Spider.create(new IqiyiDongmanPageProcessor()).thread(2);
		List<ResultItems> resultItemses = spider.getAll(Arrays.asList("http://www.iqiyi.com/dongman"));
		for (ResultItems resultItem : resultItemses) {

		}
	}

	protected List<ProcessUrlEntity> getProcessUrlList() {
		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findByType("episode");
		return list;
	}

}
