package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import xie.playdatacollect.collector.quartz.utils.XRefreshSchedule;

import javax.annotation.Resource;

public class XRefreshScheduleJob extends XBaseQuartzJobBean {

	@Resource
	XRefreshSchedule refreshSchedule;

	@Override
	protected void executeJob(JobExecutionContext context) {
		refreshSchedule.refreshSchedule();
	}

}
