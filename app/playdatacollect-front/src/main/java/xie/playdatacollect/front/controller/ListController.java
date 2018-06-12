package xie.playdatacollect.front.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.spring.jpa.entity.EntityCache;
import xie.common.utils.java.XReflectionUtils;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;
import xie.framework.web.controller.BaseController;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.influxdb.action.XInfluxdbAction;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

		String classAndMethodStr = XReflectionUtils.getThisClassAndMethodStr();

		List<ProgramEntity> programEntityList = entityCache.get(classAndMethodStr + ".programEntityList",
				() -> {
					Sort sort = new Sort(Sort.Direction.ASC, "fullName");
					return programService.findAll(sort);
				}
		);
		Collator collator = Collator.getInstance(java.util.Locale.CHINESE);
		programEntityList.sort((o1, o2) -> collator.compare(o1.getFullName(), o2.getFullName()));
		model.addAttribute("programEntityList", programEntityList);

		return "list";
	}

}
