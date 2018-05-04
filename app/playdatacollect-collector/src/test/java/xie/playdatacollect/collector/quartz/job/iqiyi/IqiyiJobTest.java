package xie.playdatacollect.collector.quartz.job.iqiyi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration
@ActiveProfiles(value = "test")
@EnableAutoConfiguration(exclude = {QuartzAutoConfiguration.class})
public class IqiyiJobTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("aaa")
	private String aaa;

	@Autowired
	private IqiyiPlayDataProgramJob iqiyiPlayDataProgramJob;
	@Autowired
	private IQiYiGetProcessUrl iQiYiGetProcessUrl;

	@Before
	public void before() throws Exception {
		logger.info(aaa);
		logger.info("IqiyiJobTest test start");
	}

	@After
	public void after() throws Exception {
		logger.info("IqiyiJobTest test end");
	}

	@Test
	public void testIqiyiGetProcessUrlJob() throws Exception {
		logger.info("testIqiyiGetProcessUrlJob test start");
		iQiYiGetProcessUrl.runSpider();
		Thread.sleep(5);
		logger.info("testIqiyiGetProcessUrlJob test end");
	}

	@Test
	public void testIqiyiPlayDataProgramJob() throws Exception {
		logger.info(aaa);
		logger.info("testIqiyiPlayDataProgramJob test start");
		iqiyiPlayDataProgramJob.executeJob(null);
		Thread.sleep(5);
		logger.info("testIqiyiPlayDataProgramJob test end");
	}
}
