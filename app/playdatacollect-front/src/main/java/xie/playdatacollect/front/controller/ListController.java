package xie.playdatacollect.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.spring.jpa.entity.EntityCache;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;
import xie.framework.web.controller.BaseController;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.influxdb.action.XInfluxdbAction;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ListController extends BaseController {

	@Resource
	EntityCache entityCache;

	@Resource
	AutoQueueDao autoQueueDao;
	@Resource
	ProgramService programService;

	@Resource
	XInfluxdbAction xInfluxdbAction;


	@RequestMapping(value = "/list")
	public String index(Model model, HttpSession session, HttpServletRequest request) {

		List<AutoQueueEntity> list = autoQueueDao.findAll();
		model.addAttribute("list", list);

		List<ProgramEntity> programEntityList = entityCache.get("aaa",
				() -> programService.findAll()
		);

		return "list";
	}

}
