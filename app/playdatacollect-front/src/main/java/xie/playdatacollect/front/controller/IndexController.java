package xie.playdatacollect.front.controller;

import org.apache.commons.lang3.time.DateUtils;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.spring.jpa.entity.EntityCache;
import xie.common.utils.date.XDateUtil;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;
import xie.framework.web.controller.BaseController;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.influxdb.action.XInfluxdbAction;
import xie.playdatacollect.influxdb.data.XInfluxdbPojoMapper;
import xie.playdatacollect.influxdb.pojo.measuerment.MPlayData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController extends BaseController {

	@Resource
	EntityCache entityCache;

	@Resource
	AutoQueueDao autoQueueDao;
	@Resource
	ProgramService programService;

	@Resource
	XInfluxdbAction xInfluxdbAction;

	private XInfluxdbPojoMapper xInfluxdbPojoMapper = new XInfluxdbPojoMapper();


	@RequestMapping(value = "/index")
	public String index(Model model, HttpSession session, HttpServletRequest request) {

		// 获取节目列表
		List<ProgramEntity> programEntityList = entityCache.get("ProgramEntityList",
				() -> programService.findAll()
		);
		model.addAttribute("programEntityList", programEntityList);


		// 获取节目对应播放量数据
		Date endDate = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DATE);
		Date startDate = DateUtils.addDays(endDate, -7);
		List<MPlayData> playDataList = entityCache.get("playDataList",
				() -> xInfluxdbAction.queryDataResultToPojo(MPlayData.class, "play_data", "day_base_data", null, startDate, endDate)
		);
		model.addAttribute("playDataList", playDataList);

		return "index";
	}

	@RequestMapping(value = "/index2")
	public String index2(Model model, HttpSession session, HttpServletRequest request) {

		List<AutoQueueEntity> list = autoQueueDao.findAll();
		model.addAttribute("list", list);

		return "index2";
	}

}
