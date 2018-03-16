package xie.playdatacollect.collector.quartz.manage;

import org.quartz.Scheduler;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

public class XQuartzManager {

	StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();

	private Scheduler scheduler;

	XQuartzManager() {
		scheduler = new StdScheduler();
	}

	public void start() {

	}

	public void end() {

	}

	public void startJob() {

	}

	public void puaseJob() {

	}

	public void endJob() {

	}

	public void updateScheduler() {

	}
}
