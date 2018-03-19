package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;
import xie.common.date.DateUtil;
import xie.common.number.XNumberUtils;
import xie.module.log.XLog;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.utils.AllServiceUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyJob extends XBaseQuartzJobBean {

	@Override
	protected void executeJob(JobExecutionContext context) throws InterruptedException {
		logger.warn("this is dummy job, {}", context.getJobDetail().getKey());
		System.out.println("this is dummy job, sleep " + context.getMergedJobDataMap().get("sleep"));

		Thread.sleep(context.getMergedJobDataMap().getLong("sleep"));
	}

}
