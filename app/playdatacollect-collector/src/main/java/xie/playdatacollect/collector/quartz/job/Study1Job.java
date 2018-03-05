package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import us.codecraft.webmagic.Spider;
import xie.common.date.DateUtil;
import xie.module.log.XLog;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliNewYear2018Processor;

import javax.annotation.Resource;
import java.util.*;

public class Study1Job extends QuartzJobBean {

	@Resource
	AllDaoUtil allDaoUtil;

	@Resource
	ProcessBilibili processBilibili;

	private String name;

	/**
	 * Invoked if a Job data map entry with that name<br>
	 * 通过job的参数自动塞入数值
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {

		XLog.info(this, DateUtil.convertToString(new Date()) + "----开始执行定时抓取任务 {} start", name);

		runSpider();

		XLog.info(this, DateUtil.convertToString(new Date()) + "抓取任务 {} end", name);
	}

	private void runSpider() {
		long dateTime = System.currentTimeMillis();

		Spider spiderBLNormal = Spider.create(new BilibiliAnimePageProcessor()).thread(2);
		Spider spiderBLNY2018 = Spider.create(new BilibiliNewYear2018Processor()).thread(2);

		Map<String, Spider> spiderMap = new HashMap<>();
		spiderMap.put(PlayDataConst.SOURCE_KEY_BILIBILI + "program", spiderBLNormal);
		spiderMap.put(PlayDataConst.SOURCE_KEY_BILIBILI + "episode", spiderBLNormal);
		spiderMap.put(PlayDataConst.SOURCE_KEY_BILIBILI + "2018拜年祭", spiderBLNY2018);

		// multi download
		List<String> listBLNormal = new ArrayList<>();
		List<String> listBLNY2018 = new ArrayList<>();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483

		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findAll();
		list.forEach((processUrl) -> {
			String key = processUrl.getSourceKey() + processUrl.getType();

			if ((PlayDataConst.SOURCE_KEY_BILIBILI + "2018拜年祭").equals(key)) {
				listBLNY2018.add(processUrl.getUrl());
			} else {
				listBLNormal.add(processUrl.getUrl());
			}
		});

		processBilibili.spiderGetAll(spiderBLNormal, listBLNormal, dateTime);
		processBilibili.spiderGetAll(spiderBLNY2018, listBLNY2018, dateTime);
	}


}
