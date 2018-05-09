package xie.module.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

	public XQuartzManager(SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
	}

	private void init(Properties properties) throws SchedulerException {
		if (properties != null) {
			stdSchedulerFactory.initialize(properties);
		}
		scheduler = stdSchedulerFactory.getScheduler();
		System.out.println(scheduler);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void start() throws SchedulerException {
		scheduler.start();
	}

	public void end() throws SchedulerException {
		scheduler.shutdown();
	}

	public void startTrigger(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		TriggerKey triggerKey = trigger.getKey();
		Trigger triggerDB = getScheduler().getTrigger(triggerKey);
		if (triggerDB == null) {
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
//			scheduler.rescheduleJob(triggerKey, trigger);

			scheduler.deleteJob(jobDetail.getKey());
			scheduler.scheduleJob(jobDetail, trigger);
		}
	}

	public void startJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public void rescheduleJob(Trigger trigger) throws SchedulerException {
		Trigger oldTrigger = getScheduler().getTrigger(trigger.getKey());
		if (oldTrigger == null) {
			scheduler.scheduleJob(oldTrigger);
		} else {
			scheduler.rescheduleJob(trigger.getKey(), trigger);
		}
	}

	public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs) throws SchedulerException {
		scheduler.scheduleJobs(triggersAndJobs, true);
	}


	public void unscheduleJob(String triggerIdentity, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerIdentity, triggerGroup);
		unscheduleJob(triggerKey);
	}

	public void unscheduleJob(TriggerKey triggerKey) throws SchedulerException {
		scheduler.unscheduleJob(triggerKey);
	}


	public void puaseJob(JobDetail jobDetail) throws SchedulerException {
		scheduler.pauseJob(jobDetail.getKey());
	}

	public void puaseJob(JobKey jobKey) throws SchedulerException {
		scheduler.pauseJob(jobKey);
	}

	public void deleteJob(JobKey jobKey) throws SchedulerException {
		scheduler.deleteJob(jobKey);
	}
}
