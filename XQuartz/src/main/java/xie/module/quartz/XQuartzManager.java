package xie.module.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

public class XQuartzManager {
	StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();

	private Scheduler scheduler;

	public XQuartzManager() throws SchedulerException {
		init(null);
	}

	public XQuartzManager(String name, int treadCount, boolean isStoreInRAM) throws SchedulerException {
		init(XQuartzManagerHelper.getMustConf(name, treadCount, isStoreInRAM));
	}

	public XQuartzManager(Properties properties) throws SchedulerException {
		init(properties);
	}

	private void init(Properties properties) throws SchedulerException {
		if (properties != null) {
			stdSchedulerFactory.initialize(properties);
		}
		scheduler = stdSchedulerFactory.getScheduler();
		System.out.println(scheduler);
	}

	public void start() throws SchedulerException {
		scheduler.start();
	}

	public void end() throws SchedulerException {
		scheduler.shutdown();
	}

	public void startJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public void rescheduleJob(Trigger trigger) throws SchedulerException {
		scheduler.rescheduleJob(trigger.getKey(), trigger);
	}

	public void puaseJob(JobDetail jobDetail) throws SchedulerException {
		scheduler.pauseJob(jobDetail.getKey());
	}

	public void puaseJob(JobKey jobKey) throws SchedulerException {
		scheduler.pauseJob(jobKey);
	}
}
