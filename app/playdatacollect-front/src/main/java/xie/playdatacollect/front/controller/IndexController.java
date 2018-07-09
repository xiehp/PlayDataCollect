package xie.playdatacollect.front.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.LazyContextVariable;
import xie.common.spring.jpa.entity.EntityCache;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.common.component.influxdb.data.XInfluxdbPojoMapper;
import xie.playdatacollect.front.controller.vo.IndexSiteXNameVo;
import xie.playdatacollect.influxdb.pojo.measurement.MDayPlayData;
import xie.playdatacollect.influxdb.pojo.measurement.MHourPlayData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class IndexController extends BaseFrontController {

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

//		// 获取时，天，周年的时间分组数据
//		List<MHourPlayData> hourPlayDataList = controllerUtils.getPlayCountData(1, "1h");
//		List<MHourPlayData> dayPlayDataList = controllerUtils.getPlayCountData(2, "1d");
//		List<MHourPlayData> weekPlayDataList = controllerUtils.getPlayCountData(15, "1w");
//		List<MHourPlayData> monthPlayDataList = controllerUtils.getPlayCountData(63, "30d");
//
//		// 以名字命名的时，天，周年的时间分组数据
//		Map<String, List<MHourPlayData>> hourPlayDataMap = controllerUtils.getPlayDataMap(hourPlayDataList);
//		Map<String, List<MHourPlayData>> dayPlayDataMap = controllerUtils.getPlayDataMap(dayPlayDataList);
//		Map<String, List<MHourPlayData>> weekPlayDataMap = controllerUtils.getPlayDataMap(weekPlayDataList);
//		Map<String, List<MHourPlayData>> monthPlayDataMap = controllerUtils.getPlayDataMap(monthPlayDataList);
//
//		// 获取时，天，周年的时间分组的整合数据
//		Map<String, List<MHourPlayData>> hourPlayDataSumMap = controllerUtils.getPlayDataSumMap(hourPlayDataList);
//		Map<String, List<MHourPlayData>> dayPlayDataSumMap = controllerUtils.getPlayDataSumMap(dayPlayDataList);
//		Map<String, List<MHourPlayData>> weekPlayDataSumMap = controllerUtils.getPlayDataSumMap(weekPlayDataList);
//		Map<String, List<MHourPlayData>> monthPlayDataSumMap = controllerUtils.getPlayDataSumMap(monthPlayDataList);
//
//		model.addAttribute("hourPlayDataMap", hourPlayDataMap);
//		model.addAttribute("dayPlayDataMap", dayPlayDataMap);
//		model.addAttribute("weekPlayDataMap", weekPlayDataMap);
//		model.addAttribute("monthPlayDataMap", monthPlayDataMap);
//
//		model.addAttribute("hourPlayDataSumMap", hourPlayDataSumMap);
//		model.addAttribute("dayPlayDataSumMap", dayPlayDataSumMap);
//		model.addAttribute("weekPlayDataSumMap", weekPlayDataSumMap);
//		model.addAttribute("monthPlayDataSumMap", monthPlayDataSumMap);

		model.addAttribute("dateFormatStr", "yyyy-MM-dd");


		// 构造每一行的数据

		IndexSiteXNameVo indexSiteXNameVo = controllerUtils.getPlayDataSiteMap();
		model.addAttribute("indexSiteXNameVo", indexSiteXNameVo);
		model.addAttribute("PlayDataConst", PlayDataConst.class);
		model.addAttribute("playDataConst", new PlayDataConst());


		// 当前播放量

		// 一小时播放量差值
		// 一小时播放量差值
		// 一小时播放量差值

		return "index";
	}

	@RequestMapping(value = "/index2")
	public String index2(Model model, HttpSession session, HttpServletRequest request) {

		List<AutoQueueEntity> list = autoQueueDao.findAll();
		model.addAttribute("list", list);

		return "index2";
	}

}
