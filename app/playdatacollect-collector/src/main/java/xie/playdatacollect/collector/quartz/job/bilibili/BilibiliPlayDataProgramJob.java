package xie.playdatacollect.collector.quartz.job.bilibili;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.utils.AllDaoUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliNewYear2018Processor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BilibiliPlayDataProgramJob extends XBaseQuartzJobBean {

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

		Spider spiderBLNormal = Spider.create(new BilibiliAnimePageProcessor()).thread(2);

		// multi download
		List<String> listBLNormal = new ArrayList<>();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483

		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findBySourceKeyAndType(PlayDataConst.SOURCE_KEY_BILIBILI, PlayDataConst.SOURCE_TYPE_PROGRAM);
		list.forEach((processUrl) -> {
			listBLNormal.add(processUrl.getUrl());
		});

		processBilibili.spiderGetAll(spiderBLNormal, listBLNormal, dateTime);
	}


}
