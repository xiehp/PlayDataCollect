package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class XBaseQuartzJobBean extends QuartzJobBean {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info(" {}({}) start by {}", this.getClass().getSimpleName(), name, context.getTrigger());

		try {
			executeJob(context);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + " {} error by {}, {}", name, context.getTrigger(), e.getMessage());
			throw new JobExecutionException(e);
		} finally {
			logger.info(" {}({}) end by {}", this.getClass().getSimpleName() + name, context.getTrigger());
		}

	}


	protected abstract void executeJob(JobExecutionContext context) throws JobExecutionException, InterruptedException;
}
