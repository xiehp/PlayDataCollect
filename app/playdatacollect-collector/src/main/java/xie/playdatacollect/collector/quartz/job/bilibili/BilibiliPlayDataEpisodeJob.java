package xie.playdatacollect.collector.quartz.job.bilibili;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.data.domain.Page;
import us.codecraft.webmagic.Spider;
import xie.common.utils.date.DateUtil;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.core.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class BilibiliPlayDataEpisodeJob extends XBaseQuartzJobBean {

	@Resource
	AllDaoUtil allDaoUtil;
	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	ProcessBilibili processBilibili;

	/** 当前往前多少时间，秒 */
	private String beforeSecond;
	/** 当前往后多少时间，秒 */
	private String afterSecond;

	public void setBeforeSecond(String beforeSecond) {
		this.beforeSecond = beforeSecond;
	}

	public void setAfterSecond(String afterSecond) {
		this.afterSecond = afterSecond;
	}

	@Override
	protected void executeJob(JobExecutionContext context) {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		runSpider(jobDataMap);
	}

	private void runSpider(JobDataMap jobDataMap) {
		long dateTime = System.currentTimeMillis();

		Spider spider = Spider.create(new BilibiliAnimePageProcessor()).thread(3);

		// multi download
		List<String> listUrl = new ArrayList<>();

		// http://api.bilibili.com/archive_stat/stat?aid=18168483

		List<ProcessUrlEntity> listProcessUrl = getProcessUrlList(jobDataMap);
		listProcessUrl.forEach((processUrl) -> {
			if (processUrl.getUrl() != null) {
				listUrl.add(processUrl.getUrl());
			} else {
				logger.warn("{} 的url为空，无法获取数据。", processUrl.getName());
			}
		});

		processBilibili.spiderGetAll(spider, listUrl, dateTime);
	}

	protected List<ProcessUrlEntity> getProcessUrlList(JobDataMap jobDataMap) {
		List<ProcessUrlEntity> list = new ArrayList<>();
		Date begin = null;
		Date end = null;

		try {
			if (beforeSecond != null) {
				begin = Date.from(LocalDateTime.now().minusSeconds(Long.valueOf(beforeSecond)).atZone(ZoneId.systemDefault()).toInstant());
			}
		} catch (Exception e) {
			logger.error("转换日期发生错误:{}", beforeSecond, e);
		}
		try {
			if (afterSecond != null) {
				end = Date.from(LocalDateTime.now().minusSeconds(Long.valueOf(afterSecond)).atZone(ZoneId.systemDefault()).toInstant());
			}
		} catch (Exception e) {
			logger.error("转换日期发生错误:{}", afterSecond, e);
		}

		if (begin != null || end != null) {
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("EQ_sourceKey", PlayDataConst.SOURCE_KEY_BILIBILI);
			searchMap.put("EQ_type", PlayDataConst.SOURCE_TYPE_EPISODE);

			// 搜索总数
			Page<ProcessUrlEntity> pageTotal = allServiceUtil.getProcessUrlService().searchPageByParams(searchMap, ProcessUrlEntity.class);
			long totalCount = pageTotal.getTotalElements();

			// 搜索需处理数据
			if (begin != null) {
				searchMap.put("GTE_beginDate", begin);
			}
			if (end != null) {
				searchMap.put("LTE_beginDate", end);
			}
			Page<ProcessUrlEntity> page = allServiceUtil.getProcessUrlService().searchPageByParams(searchMap, ProcessUrlEntity.class);
			list = page.getContent();
			logger.info("{}, 当前需处理数量：{}, 开始时间：{}，结束时间：{}，所有数量：{}", getName(), list.size(), DateUtil.convertToString(begin), DateUtil.convertToString(end), totalCount);
		} else {
			logger.error("参数设定错误，不能同时为空。beforeSecond:{}, afterSecond:{}", beforeSecond, afterSecond);
		}

		return list;
	}

}
