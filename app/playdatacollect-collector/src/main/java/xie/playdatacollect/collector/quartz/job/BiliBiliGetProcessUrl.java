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

public class BiliBiliGetProcessUrl extends QuartzJobBean {

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
		XLog.info(this, DateUtil.convertToString(new Date()) + this.getClass().getName() + " start", name);


		

		XLog.info(this, DateUtil.convertToString(new Date()) + this.getClass().getName() + " end", name);
	}


}
