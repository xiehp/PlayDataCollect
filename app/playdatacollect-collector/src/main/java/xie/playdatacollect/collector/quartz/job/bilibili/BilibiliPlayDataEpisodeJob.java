package xie.playdatacollect.collector.quartz.job.bilibili;

import org.quartz.JobExecutionContext;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.core.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public abstract class BilibiliPlayDataEpisodeJob extends XBaseQuartzJobBean {

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

		Spider spiderBLNormal = Spider.create(new BilibiliAnimePageProcessor()).thread(2);

		// multi download
		List<String> listBLNormal = new ArrayList<>();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483

		List<ProcessUrlEntity> list = getProcessUrlList();
		list.forEach((processUrl) -> {
			if (processUrl.getUrl() != null) {
				listBLNormal.add(processUrl.getUrl());
			} else {
				logger.warn("{} 的url为空，无法获取数据。", processUrl.getName());
			}
		});

		processBilibili.spiderGetAll(spiderBLNormal, listBLNormal, dateTime);
	}

	protected List<ProcessUrlEntity> getProcessUrlList() {
		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findByType("episode");
		return list;
	}

}
