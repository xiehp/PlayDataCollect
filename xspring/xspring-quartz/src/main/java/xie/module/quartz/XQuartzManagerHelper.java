package xie.module.quartz;

import org.quartz.SchedulerException;

import java.util.Properties;

public class XQuartzManagerHelper {

	/**
	 * 最低配置<br />
	 * <br />
	 * org.quartz.scheduler.instanceName = MyScheduler<br />
	 * org.quartz.threadPool.threadCount = 3<br />
	 * org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore<br />
	 */
	public static Properties getMustConf(String name, int treadCount, boolean isStoreInRAM) {
		Properties p = new Properties();
		p.setProperty("org.quartz.scheduler.instanceName", name);
		p.setProperty("org.quartz.threadPool.threadCount", String.valueOf(treadCount));
		if (isStoreInRAM) {
			p.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		} else {
			p.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		}

		return p;
	}

	public static void main(String[] args) throws SchedulerException {
		XQuartzManager xQuartzManager1 = new XQuartzManager();
		XQuartzManager xQuartzManager2 = new XQuartzManager();
		XQuartzManager xQuartzManager3 = new XQuartzManager();
		XQuartzManager xQuartzManager5 = new XQuartzManager("5",  5, true);
		XQuartzManager xQuartzManager6 = new XQuartzManager(getMustConf("6",  6, true));
		XQuartzManager xQuartzManager61 = new XQuartzManager(getMustConf("6",  61, true));


	}

}
