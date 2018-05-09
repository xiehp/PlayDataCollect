package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;

public class NoJob extends XBaseQuartzJobBean {

	@Override
	public void executeJob(JobExecutionContext context) {
		logger.warn("this is no job");
	}

}
