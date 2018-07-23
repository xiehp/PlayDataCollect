package xie.playdatacollect.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.common.spring.jpa.entity.EntityCache;
import xie.common.utils.constant.XConst;
import xie.common.utils.exception.XException;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.influxdb.pojo.measurement.MPlayData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping(value = "/table")
public class TableController extends BaseFrontController {

	@Resource
	EntityCache entityCache;

	@Resource
	AutoQueueDao autoQueueDao;
	@Resource
	ProgramService programService;

	@Resource
	XInfluxdbAction xInfluxdbAction;

	@Resource
	ControllerUtils controllerUtils;

	@RequestMapping(value = "/programTrend/{programCode}")
	public String hour(Model model, HttpSession session, HttpServletRequest request,
					   @PathVariable Long programCode) throws XException {

		ProgramEntity programEntity = entityCache.get(() -> programService.findByCode(programCode));

		if (programEntity == null) {
			throw new XException("节目ID不存在");
		}

		String programName = programEntity.getFullName();
		List<MPlayData> playDataList = controllerUtils.getMaxPlayCountData(null, Instant.now().minusSeconds(XConst.SECOND_06_MONTH), Instant.now(), "1d", programName);

		model.addAttribute("programEntity", programEntity);
		model.addAttribute("playDataList", playDataList);

		return "table/programTrend";
	}


}
