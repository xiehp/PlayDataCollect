package xie.playdatacollect.collector.quartz.job.bilibili;

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
import xie.playdatacollect.collector.quartz.job.iqiyi.IQiYiGetProcessUrl;
import xie.playdatacollect.collector.quartz.job.iqiyi.IqiyiPlayDataProgramJob;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration
@ActiveProfiles(value = "test")
@EnableAutoConfiguration(exclude = {QuartzAutoConfiguration.class})
public class BilibiliJobTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("aaa")
	private String aaa;

	@Autowired
	private BilibiliPlayDataProgramJob bilibiliPlayDataProgramJob;
	@Autowired
	private BiliBiliGetProcessUrl bilibiliGetProcessUrl;

	@Before
	public void before() throws Exception {
		logger.info(aaa);
		logger.info("BilibiliJobTest test start");
	}

	@After
	public void after() throws Exception {
		logger.info("BilibiliJobTest test end");
	}

	@Test
	public void testBilibiliGetProcessUrlJob() throws Exception {
		logger.info("testBilibiliGetProcessUrlJob test start");
		bilibiliGetProcessUrl.executeJob(null);
		Thread.sleep(5);
		logger.info("testBilibiliGetProcessUrlJob test end");
	}

	@Test
	public void testBilibiliPlayDataProgramJob() throws Exception {
		logger.info(aaa);
		logger.info("testBilibiliPlayDataProgramJob test start");
		bilibiliPlayDataProgramJob.executeJob(null);
		Thread.sleep(5);
		logger.info("testBilibiliPlayDataProgramJob test end");
	}
}
