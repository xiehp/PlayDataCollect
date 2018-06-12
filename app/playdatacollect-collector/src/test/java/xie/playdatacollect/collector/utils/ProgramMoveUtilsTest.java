package xie.playdatacollect.collector.utils;

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
import xie.common.component.influxdb.exception.XInfluxdbException;

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
		programMoveUtils.moveProgram(
				"ce66a348-03e9-4a5e-8802-3dfa7a4b675d",
				"c7694ad3-bec3-4e5a-8e9e-d76d1cad66c3");
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
