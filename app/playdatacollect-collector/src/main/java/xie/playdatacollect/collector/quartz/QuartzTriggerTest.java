package xie.playdatacollect.collector.quartz;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xie.module.log.XLog;
import xie.module.quartz.XQuartzManager;
import xie.playdatacollect.base.db.entity.BaseEntity;
import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.collector.quartz.config.QuartzJobDetailConfig;
import xie.playdatacollect.collector.quartz.config.QuartzTriggerConfig;
import xie.playdatacollect.collector.quartz.utils.XCronConfig;
import xie.playdatacollect.core.utils.AllDaoUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用于初始化一些基础数据
 */
@Component
@Order(0)
public class QuartzTriggerTest implements ApplicationRunner {

	Logger log = XLog.getLogger(QuartzTriggerTest.class);

	@Resource
	AllDaoUtil allDaoUtil;

	XQuartzManager xQuartzManager = new XQuartzManager();

	@Resource
	QuartzJobDetailConfig quartzJobDetailConfig;
	QuartzTriggerConfig quartzTriggerConfig = new QuartzTriggerConfig();

	public QuartzTriggerTest() throws SchedulerException {

	}

	@Override
	public void run(ApplicationArguments args) throws SchedulerException, InterruptedException {

		JobDetail jobDetail1 =  quartzJobDetailConfig.dummyJobDetail1();
		JobDetail jobDetail11 =  quartzJobDetailConfig.dummyJobDetail1();
		JobDetail jobDetail2 =  quartzJobDetailConfig.dummyJobDetail2();
		JobDetail jobDetail3 =  quartzJobDetailConfig.dummyJobDetail3();

		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForTotalCount(10).withIntervalInSeconds(2);
		Trigger trigger1 = quartzTriggerConfig.createTrigger(scheduleBuilder, jobDetail1, "test1");
		xQuartzManager.startJob(jobDetail1, trigger1);

		xQuartzManager.start();

		Thread.sleep(3);
		Trigger trigger2 = quartzTriggerConfig.createCronTrigger(jobDetail2, "test2", XCronConfig.PER_02_SECOND);
		xQuartzManager.startJob(jobDetail2, trigger2);

		Trigger trigger3 = quartzTriggerConfig.createCronTrigger(jobDetail3, "test3", XCronConfig.PER_03_SECOND);
		xQuartzManager.startJob(jobDetail3, trigger3);
	}

}