package xie.playdatacollect.front.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.spring.jpa.entity.EntityCache;
import xie.common.utils.java.XReflectionUtils;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.common.component.influxdb.action.XInfluxdbAction;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.Collator;
import java.util.List;

@Controller
@RequestMapping(value = "list")
public class ListController extends BaseFrontController {

	@Resource
	EntityCache entityCache;

	@Resource
	AutoQueueDao autoQueueDao;
	@Resource
	ProgramService programService;

	@Resource
	XInfluxdbAction xInfluxdbAction;


	@RequestMapping(value = "hour")
	public String hour(Model model, HttpSession session, HttpServletRequest request) {

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


	@RequestMapping(value = "day")
	public String day(Model model, HttpSession session, HttpServletRequest request) {

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


	@RequestMapping(value = "week")
	public String week(Model model, HttpSession session, HttpServletRequest request) {

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


	@RequestMapping(value = "month")
	public String month(Model model, HttpSession session, HttpServletRequest request) {

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
