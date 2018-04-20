package xie.playdatacollect.collector.quartz.job.iqiyi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration()
public class IqiyiPlayDataProgramJobTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IqiyiPlayDataProgramJob iqiyiPlayDataProgramJob;

	@Before
	public void before() throws Exception {
		logger.info("IQiYiGetProcessUrlTest test start");
	}

	@After
	public void after() throws Exception {
		logger.info("IQiYiGetProcessUrlTest test end");
	}

	@Test
	public void test1() throws Exception {
		logger.info("IQiYiGetProcessUrlTest1 test start");
		Thread.sleep(5);
		logger.info("IQiYiGetProcessUrlTest1 test end");
	}

	@Test
	public void test2() throws Exception {
		logger.info("IQiYiGetProcessUrlTest2 test start");
		iqiyiPlayDataProgramJob.executeJob(null);
		Thread.sleep(5);
		logger.info("IQiYiGetProcessUrlTest2 test end");
	}
}
