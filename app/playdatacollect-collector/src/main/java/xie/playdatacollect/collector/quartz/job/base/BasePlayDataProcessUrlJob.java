package xie.playdatacollect.collector.quartz.job.base;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.data.domain.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.common.utils.date.DateUtil;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.collector.workflow.get.IGetDataProcess;
import xie.playdatacollect.collector.workflow.use.IUseDataProcess;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.utils.AllDaoUtil;
import xie.playdatacollect.core.db.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public abstract class BasePlayDataProcessUrlJob extends XBaseQuartzJobBean {

	@Resource
	private AllDaoUtil allDaoUtil;
	@Resource
	protected AllServiceUtil allServiceUtil;

	@Override
	public void executeJob(JobExecutionContext context) {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		runSpider(jobDataMap);
	}

	public void runSpider(JobDataMap jobDataMap) {
		long dateTime = System.currentTimeMillis();

		// 获取待处理URL
		List<String> listUrl = new ArrayList<>();
		List<ProcessUrlEntity> listProcessUrl = getProcessUrlList(jobDataMap);
		if (listProcessUrl != null) {
			listProcessUrl.forEach((processUrl) -> {
				if (processUrl.getUrl() != null) {
					listUrl.add(processUrl.getUrl());
				} else {
					logger.warn("{} 的url为空，无法获取数据。", processUrl.getName());
				}
			});

			// 根据URL获取数据
			IGetDataProcess getDataProcess = getIGetDataProcess();
			List<CollectedData> collectedDataList = getDataProcess.getDataList(listProcessUrl, jobDataMap);
//
//
//			// TODO 获得PageProcessor
//			PageProcessor pageProcessor = getPageProcessor();
//			Spider spider = Spider.create(pageProcessor).thread(3);
//			getIUseDataProcess().spiderGetAll(spider, listUrl, dateTime);

			// 过滤数据

			// 处理数据
			IUseDataProcess useDataProcess = getIUseDataProcess();
			useDataProcess.use(collectedDataList, dateTime);
		}
	}

	/**
	 * 结果数据处理类，获得对数据的处理类
	 */
	protected abstract IGetDataProcess getIGetDataProcess();

	/**
	 * 结果数据处理类，获得对数据的处理类
	 */
	protected abstract IUseDataProcess getIUseDataProcess();

	protected List<ProcessUrlEntity> getProcessUrlList(JobDataMap jobDataMap) {
		String sourceKey = jobDataMap.getString("sourceKey");
		String type = jobDataMap.getString("type");
		/** 当前往前多少时间，秒 */
		Long beforeSecond = null;
		if (jobDataMap.containsKey("beforeSecond")) {
			beforeSecond = jobDataMap.getLongValue("beforeSecond");
		}
		/** 当前往后多少时间，秒 */
		Long afterSecond = null;
		if (jobDataMap.containsKey("afterSecond")) {
			afterSecond = jobDataMap.getLongValue("afterSecond");
		}

		Date begin = null;
		Date end = null;

		logger.error("准备获取url列表数据 {}", jobDataMap);
		boolean hasErrorFlag = false;
		if (XStringUtils.isBlank(sourceKey)) {
			logger.error("sourceKey不能为空");
			hasErrorFlag = true;
		}
		if (XStringUtils.isBlank(type)) {
			logger.error("type不能为空");
			hasErrorFlag = true;
		}

		try {
			if (beforeSecond != null) {
				begin = Date.from(LocalDateTime.now().minusSeconds(Long.valueOf(beforeSecond)).atZone(ZoneId.systemDefault()).toInstant());
			}
		} catch (Exception e) {
			logger.error("转换开始日期发生错误:{}", beforeSecond, e);
			hasErrorFlag = true;
		}

		try {
			if (afterSecond != null) {
				end = Date.from(LocalDateTime.now().minusSeconds(Long.valueOf(afterSecond)).atZone(ZoneId.systemDefault()).toInstant());
			}
		} catch (Exception e) {
			logger.error("转换结束日期发生错误:{}", afterSecond, e);
			hasErrorFlag = true;
		}

		if (begin == null && end == null) {
			logger.error("参数设定错误，指定时间不能同时为空。");
			hasErrorFlag = true;
		}

		if (hasErrorFlag) {
			return null;
		}

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("EQ_sourceKey", sourceKey);
		searchMap.put("EQ_type", type);

		// 搜索总数
		long totalCount = allServiceUtil.getProcessUrlService().countByParams(searchMap, ProcessUrlEntity.class);

		// 搜索需处理数据
		if (begin != null) {
			searchMap.put("GTE_reBeginDate", begin);
		}
		if (end != null) {
			searchMap.put("LTE_reBeginDate", end);
		}
		Page<ProcessUrlEntity> page = allServiceUtil.getProcessUrlService().searchPageByParams(searchMap, ProcessUrlEntity.class);
		List<ProcessUrlEntity> list = page.getContent();
		logger.info("{}, 当前需处理数量：{}, 来源：{}, 类型：{}, 开始时间：{}，结束时间：{}，该类型所有数量：{}", getName(), list.size(), sourceKey, type, DateUtil.convertToString(begin), DateUtil.convertToString(end), totalCount);

		return list;
	}

}
