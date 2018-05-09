package xie.playdatacollect.collector.quartz.job.bilibili;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDataMap;
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
import xie.common.utils.constant.XConst;
import xie.playdatacollect.common.PlayDataConst;

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
	private BilibiliPlayDataEpisodeJob bilibiliPlayDataEpisodeJob;
	@Autowired
	private BiliBiliGetProcessUrl bilibiliGetProcessUrl;

	@Before
	public void before() {
		logger.info("----------------------before---------------------");
		logger.info("BilibiliJobTest test start");
	}

	@After
	public void after() {
		logger.info("BilibiliJobTest test end");
		logger.info("----------------------after---------------------");
	}

	@Test
	public void testBilibiliGetProcessUrlJob() {
		logger.info("testBilibiliGetProcessUrlJob test start");
		bilibiliGetProcessUrl.executeJob(null);
		logger.info("testBilibiliGetProcessUrlJob test end");
	}

	@Test
	public void testBilibiliPlayDataProgramJob() {
		logger.info("testBilibiliPlayDataProgramJob test start");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("name", "testBilibiliPlayDataProgramJob");
		jobDataMap.put("sourceKey", PlayDataConst.SOURCE_KEY_BILIBILI);
		jobDataMap.put("type", PlayDataConst.SOURCE_TYPE_PROGRAM);
		jobDataMap.put("beforeSecond", XConst.SECOND_01_DAY);
		bilibiliPlayDataProgramJob.runSpider(jobDataMap);
		logger.info("testBilibiliPlayDataProgramJob test end");
	}

	@Test
	public void testBilibiliPlayDataEpisodeJob() {
		logger.info("testBilibiliPlayDataEpisodeJob test start");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("name", "testBilibiliPlayDataEpisodeJob");
		jobDataMap.put("sourceKey", PlayDataConst.SOURCE_KEY_BILIBILI);
		jobDataMap.put("type", PlayDataConst.SOURCE_TYPE_EPISODE);
		jobDataMap.put("beforeSecond", XConst.SECOND_01_DAY);
		jobDataMap.put("afterSecond", XConst.SECOND_01_HOUR);
		bilibiliPlayDataEpisodeJob.runSpider(jobDataMap);
		logger.info("testBilibiliPlayDataEpisodeJob test end");
	}
}
