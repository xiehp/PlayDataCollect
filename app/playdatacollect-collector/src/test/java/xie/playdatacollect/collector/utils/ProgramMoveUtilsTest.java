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
import java.util.Date;
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
		programMoveUtils.moveProgram("426d6181-7821-4dc2-973c-8930b40178ac", "12a80cdd-5120-458e-8222-709b29a9a24a");
	}

	@Test
	public void do_createProgram() {
		List<ProcessUrlEntity> list = processUrlDao.findByType(PlayDataConst.SOURCE_TYPE_PROGRAM);
		list.forEach((processUrlEntity -> {
			if (processUrlEntity.getReBeginDate() != null && processUrlEntity.getRecentBeginDate().after(new Date())) {
				processUrlUtils.saveProcessUrlData(processUrlEntity.getSourceKey(), processUrlEntity.getName(), processUrlEntity.getType(), processUrlEntity.getRemark(), processUrlEntity.getUrl(), new Date(processUrlEntity.getReBeginDate().getTime() + 1), XJsonUtil.fromJsonString(processUrlEntity.getParams()));

			}
		}));
	}
}
