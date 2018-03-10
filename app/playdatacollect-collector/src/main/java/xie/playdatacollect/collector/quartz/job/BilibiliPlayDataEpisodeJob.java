package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import us.codecraft.webmagic.Spider;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliNewYear2018Processor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BilibiliPlayDataEpisodeJob extends XBaseQuartzJobBean {

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
		List<String> listBLNY2018 = new ArrayList<>();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483

		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findByType("episode");
		list.forEach((processUrl) -> {
			String key = processUrl.getSourceKey() + processUrl.getType();

			if ((PlayDataConst.SOURCE_KEY_BILIBILI + "2018拜年祭").equals(key)) {
				listBLNY2018.add(processUrl.getUrl());
			} else {
				listBLNormal.add(processUrl.getUrl());
			}
		});

		processBilibili.spiderGetAll(spiderBLNormal, listBLNormal, dateTime);
	}


}
