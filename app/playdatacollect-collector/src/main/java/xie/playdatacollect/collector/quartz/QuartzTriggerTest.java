package xie.playdatacollect.collector.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import xie.common.utils.log.XLog;
import xie.module.quartz.XQuartzManager;
import xie.playdatacollect.collector.quartz.config.QuartzJobDetailConfig;
import xie.playdatacollect.collector.quartz.config.QuartzTriggerConfig;
import xie.playdatacollect.core.db.utils.AllDaoUtil;

import javax.annotation.Resource;

/**
 * 用于初始化一些基础数据
 */
//@Component
@Order(0)
public class QuartzTriggerTest implements ApplicationRunner {

	Logger log = XLog.getLog(QuartzTriggerTest.class);

	@Resource
	AllDaoUtil allDaoUtil;

	@Resource
	XQuartzManager xQuartzManager;

	@Resource
	SchedulerFactoryBean schedulerFactoryBean;

	@Resource
	Scheduler scheduler;

	@Resource
	QuartzJobDetailConfig quartzJobDetailConfig;

	@Resource
	QuartzTriggerConfig quartzTriggerConfig;

	@Override
	public void run(ApplicationArguments args) throws InterruptedException {
//
//
//		JobDetail jobDetail1 = quartzJobDetailConfig.dummyJobDetail1();
//		JobDetail jobDetail11 = quartzJobDetailConfig.dummyJobDetail1();
//
//		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForTotalCount(100).withIntervalInSeconds(5);
//		Trigger trigger1 = quartzTriggerConfig.createTrigger(scheduleBuilder, jobDetail1, "test1");
//		try {
//			xQuartzManager.startTrigger(trigger1);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//
//		try {
////			xQuartzManager.start();
//			if (!xQuartzManager.getScheduler().isStarted()) {
//				xQuartzManager.getScheduler().start();
//			}
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//
//		runOtherTrigger();
	}

	private void runOtherTrigger() throws InterruptedException {
//		JobDetail jobDetail2 = quartzJobDetailConfig.dummyJobDetail2();
//		JobDetail jobDetail3 = quartzJobDetailConfig.dummyJobDetail3();
//		JobDetail jobDetail4 = quartzJobDetailConfig.dummyJobDetail3();
//
//		Thread.sleep(3);
//		Trigger trigger2 = quartzTriggerConfig.createCronTrigger(jobDetail2, "test2", XScheduleConfig.PER_10_SECOND);
//		try {
//			xQuartzManager.startTrigger(trigger2);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//
//		Thread.sleep(3);
//		Trigger trigger3 = quartzTriggerConfig.createCronTrigger(jobDetail3, "test3", XScheduleConfig.PER_15_SECOND);
//		try {
//			xQuartzManager.startTrigger(trigger3);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//
//		Thread.sleep(3);
//		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(10);
//
//		Trigger trigger4 = quartzTriggerConfig.createTrigger(scheduleBuilder, jobDetail4, "test4");
//		try {
//			xQuartzManager.startTrigger(trigger4);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
	}

}