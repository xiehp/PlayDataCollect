package xie.playdatacollect.collector.utils;

import org.eclipse.jetty.util.ProcessorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import xie.common.spring.utils.XJsonUtil;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.dao.url.ProcessUrlDao;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.service.url.ProcessUrlService;
import xie.playdatacollect.core.db.utils.ProcessUrlUtils;
import xie.playdatacollect.influxdb.exception.XInfluxdbException;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration
@ActiveProfiles(value = "test")
@EnableAutoConfiguration(exclude = {QuartzAutoConfiguration.class})
public class ProgramMoveUtilsTest {

	@Resource
	ProgramMoveUtils programMoveUtils;

	@Resource
	ProcessUrlDao processUrlDao;
	@Resource
	ProcessUrlService processUrlService;
	@Resource
	ProcessUrlUtils processUrlUtils;

	@Test
	public void do_moveProgram() throws ParseException, XInfluxdbException {
		programMoveUtils.moveProgram("66ef2df6-cbd7-4870-8437-49e6bd317ef4", "90c9df1d-b83c-47e0-9a32-07823625577e");
	}

	@Test
	public void do_createProgram() throws ParseException, XInfluxdbException {
		List<ProcessUrlEntity> list = processUrlDao.findByType(PlayDataConst.SOURCE_TYPE_PROGRAM);
		list.forEach((processUrlEntity -> {
			processUrlUtils.saveProcessUrlData(processUrlEntity.getSourceKey(), processUrlEntity.getName(), processUrlEntity.getType(), processUrlEntity.getRemark(), processUrlEntity.getUrl(), processUrlEntity.getReBeginDate(), XJsonUtil.fromJsonString(processUrlEntity.getParams()));
		}));
	}
}
