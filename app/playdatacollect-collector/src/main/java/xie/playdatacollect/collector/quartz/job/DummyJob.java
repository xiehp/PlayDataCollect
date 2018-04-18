package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;

public class DummyJob extends XBaseQuartzJobBean {

	@Override
	protected void executeJob(JobExecutionContext context) throws InterruptedException {
		logger.warn("this is dummy job, {}", context.getJobDetail().getKey());
		System.out.println("this is dummy job, sleep " + context.getMergedJobDataMap().get("sleep"));

		Long time = context.getMergedJobDataMap().containsKey("sleep") ? context.getMergedJobDataMap().getInt("sleep") : 10L;
		if (time == null) {
			logger.warn("time is null");
			time = 10L;
		}
		Thread.sleep(time);
	}

}
