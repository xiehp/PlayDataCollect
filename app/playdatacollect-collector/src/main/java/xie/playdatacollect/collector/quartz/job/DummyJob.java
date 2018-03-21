package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;

public class DummyJob extends XBaseQuartzJobBean {

	@Override
	protected void executeJob(JobExecutionContext context) throws InterruptedException {
		logger.warn("this is dummy job, {}", context.getJobDetail().getKey());
		System.out.println("this is dummy job, sleep " + context.getMergedJobDataMap().get("sleep"));

		Thread.sleep(context.getMergedJobDataMap().getLong("sleep"));
	}

}
