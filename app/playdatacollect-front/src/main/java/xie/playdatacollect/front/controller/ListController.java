package xie.playdatacollect.front.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xie.common.component.influxdb.action.XInfluxdbActionParameter;
import xie.common.spring.jpa.entity.EntityCache;
import xie.common.utils.constant.XConst;
import xie.common.utils.date.XDateUtil;
import xie.common.utils.java.XReflectionUtils;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.playdatacollect.influxdb.pojo.measurement.MDayPlayData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

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

	@Resource
	ControllerUtils controllerUtils;


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

		return "list/day";
	}


	@RequestMapping(value = "day")
	public String day(Model model, HttpSession session, HttpServletRequest request) {

		controllerUtils.searchPlayCountData(model, 7, "1d");
		model.addAttribute("dateFormatStr", "yyyy-MM-dd");

		return "list/day";
	}


	@RequestMapping(value = "week")
	public String week(Model model, HttpSession session, HttpServletRequest request) {

		controllerUtils.searchPlayCountData(model, 30, "1w");
		model.addAttribute("dateFormatStr", "yyyy-MM-dd");

		return "list/day";
	}


	@RequestMapping(value = "month")
	public String month(Model model, HttpSession session, HttpServletRequest request) {

		controllerUtils.searchPlayCountData(model, 30, "30d");
		model.addAttribute("dateFormatStr", "yyyy-MM-dd");

		return "list/day";
	}

}
