package xie.playdatacollect.collector.job;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.common.date.DateUtil;
import xie.common.json.XJsonUtil;
import xie.common.string.XStringUtils;
import xie.module.log.XLog;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliNewYear2018Processor;
import xie.playdatacollect.utils.PlayDataUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Study1Job extends QuartzJobBean {

	@Resource
	AllDaoUtil allDaoUtil;

	@Resource
	ProcessBilibili processBilibili;

	private String name;

	/**
	 * Invoked if a Job data map entry with that name
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

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
