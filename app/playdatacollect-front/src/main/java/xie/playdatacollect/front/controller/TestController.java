package xie.playdatacollect.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.common.component.influxdb.data.XInfluxdbPojoMapper;
import xie.common.spring.jpa.entity.EntityCache;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.front.controller.vo.IndexSiteXNameVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseFrontController {

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

	private XInfluxdbPojoMapper xInfluxdbPojoMapper = new XInfluxdbPojoMapper();


	@RequestMapping(value = "/index")
	public String index(Model model, HttpSession session, HttpServletRequest request) {

//		entityCache.clear();
		// 获取节目列表
		final List<ProgramEntity> programEntityList = controllerUtils.getProgramEntityList();
		model.addAttribute("programEntityList", programEntityList);
		model.addAttribute("dateFormatStr", "yyyy-MM-dd");


		// 构造每一行的数据

		IndexSiteXNameVo indexSiteXNameVo = controllerUtils.getPlayDataSiteMapWithFirstDay();
		model.addAttribute("indexSiteXNameVo", indexSiteXNameVo);
		model.addAttribute("PlayDataConst", PlayDataConst.class);
		model.addAttribute("playDataConst", new PlayDataConst());

		return "test/index";
	}

	@RequestMapping(value = "/index2")
	public String index2(Model model, HttpSession session, HttpServletRequest request) {

		List<AutoQueueEntity> list = autoQueueDao.findAll();
		model.addAttribute("list", list);

		return "index2";
	}

}
