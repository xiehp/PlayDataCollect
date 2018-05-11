package xie.playdatacollect.collector.quartz.job.youku;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@FixMethodOrder(MethodSorters.JVM)
public class YoukuJobTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YoukuGetProcessUrl youkuGetProcessUrl;
	@Autowired
	private YoukuPlayDataProgramJob youkuPlayDataProgramJob;

	@Before
	public void before()  {
		logger.info("YoukuJobTest test start");
	}

	@After
	public void after()  {
		logger.info("YoukuJobTest test end");
	}

	@Test
	public void testYoukuGetProcessUrlJob()  {
		logger.info("testYoukuGetProcessUrlJob test start");
		youkuGetProcessUrl.runSpider();
		logger.info("testYoukuGetProcessUrlJob test end");
	}

	@Test
	public void testYoukuPlayDataProgramJob()  {
		logger.info("testYoukuPlayDataProgramJob test start");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("name", "testYoukuPlayDataEpisodeJob");
		jobDataMap.put("sourceKey", PlayDataConst.SOURCE_KEY_YOUKU);
		jobDataMap.put("type", PlayDataConst.SOURCE_TYPE_PROGRAM);
		jobDataMap.put("beforeSecond", XConst.SECOND_30_DAY);
		jobDataMap.put("afterSecond", -XConst.SECOND_01_HOUR);
		youkuPlayDataProgramJob.runSpider(jobDataMap);
		logger.info("testYoukuPlayDataProgramJob test end");
	}
}
