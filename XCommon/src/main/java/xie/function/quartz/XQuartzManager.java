package xie.function.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
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

	public void startJob(JobDetail job, Trigger trigger) throws SchedulerException {
		scheduler.scheduleJob(job, trigger);
	}

	public void puaseJob() {

	}

	public void endJob() {
		scheduler.pa
	}

	public void updateScheduler() {

	}
}
