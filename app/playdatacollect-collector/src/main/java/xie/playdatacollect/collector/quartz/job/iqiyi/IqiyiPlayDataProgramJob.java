package xie.playdatacollect.collector.quartz.job.iqiyi;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiPlayPageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IqiyiPlayDataProgramJob extends XBaseQuartzJobBean {

	@Resource
	AllDaoUtil allDaoUtil;

	@Resource
	ProcessBilibili processBilibili;

	@Override
	protected void executeJob(JobExecutionContext context) {
		runSpider();
	}

	private void runSpider() {
		long dateTime = System.currentTimeMillis();

		Spider spider = Spider.create(new IqiyiPlayPageProcessor()).thread(3);

		// multi download
		List<String> listURL = new ArrayList<>();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483

		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findBySourceKeyAndType(PlayDataConst.SOURCE_KEY_IQIYI, PlayDataConst.SOURCE_TYPE_PROGRAM);
		list.forEach((processUrl) -> {
			listURL.add(processUrl.getUrl());
		});

		processBilibili.spiderGetAll(spider, listURL, dateTime);
	}


}
