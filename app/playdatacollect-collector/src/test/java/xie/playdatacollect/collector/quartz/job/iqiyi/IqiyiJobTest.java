package xie.playdatacollect.collector.quartz.job.iqiyi;

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
public class IqiyiJobTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("aaa")
	private String aaa;

	@Autowired
	private IqiyiPlayDataProgramJob iqiyiPlayDataProgramJob;
	@Autowired
	private IQiYiGetProcessUrl iQiYiGetProcessUrl;

	@Before
	public void before()  {
		logger.info(aaa);
		logger.info("IqiyiJobTest test start");
	}

	@After
	public void after()  {
		logger.info("IqiyiJobTest test end");
	}

	@Test
	public void testIqiyiGetProcessUrlJob()  {
		logger.info("testIqiyiGetProcessUrlJob test start");
		iQiYiGetProcessUrl.runSpider();
		logger.info("testIqiyiGetProcessUrlJob test end");
	}

	@Test
	public void testIqiyiPlayDataProgramJob()  {
		logger.info("testIqiyiPlayDataProgramJob test start");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("name", "testBilibiliPlayDataEpisodeJob");
		jobDataMap.put("sourceKey", PlayDataConst.SOURCE_KEY_IQIYI);
		jobDataMap.put("type", PlayDataConst.SOURCE_TYPE_PROGRAM);
		jobDataMap.put("beforeSecond", XConst.SECOND_30_DAY);
		jobDataMap.put("afterSecond", XConst.SECOND_01_HOUR);
		iqiyiPlayDataProgramJob.runSpider(jobDataMap);
		logger.info("testIqiyiPlayDataProgramJob test end");
	}
}
