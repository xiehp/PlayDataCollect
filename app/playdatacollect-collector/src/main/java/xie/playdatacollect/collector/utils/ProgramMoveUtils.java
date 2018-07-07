package xie.playdatacollect.collector.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import xie.common.spring.autoconfigure.influx.XInfluxdbAutoConfiguration;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.core.db.dao.program.ProgramDao;
import xie.playdatacollect.core.db.dao.url.ProcessUrlDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.service.program.EpisodeService;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.core.db.utils.AllServiceUtil;
import xie.common.component.influxdb.action.XInfluxdbAction;
import xie.common.component.influxdb.exception.XInfluxdbException;
import xie.playdatacollect.influxdb.pojo.measurement.MPlayData;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Component
public class ProgramMoveUtils {

	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	ProgramService programService;
	@Resource
	ProgramDao programDao;
	@Resource
	ProcessUrlDao processUrlDao;

	@Resource
	EpisodeService episodeService;

	@Resource
	XInfluxdbAction influxdbAction;
	@Resource
	XInfluxdbAutoConfiguration a;

	/**
	 * 移动节目数据，原先URL中的节目Code将会变成新的Code<br>
	 * 同时，influxdb数据中的name tag将会变成新的
	 */
	public void moveProgram(String fromId, String toId) throws ParseException, XInfluxdbException {
		ProgramEntity programEntityFrom = programDao.findById(fromId).get();
		ProgramEntity programEntityTo = programDao.findById(toId).get();

		List<ProcessUrlEntity> processUrlEntityFromList = processUrlDao.findByProgramCode(programEntityFrom.getCode());
//		List<ProcessUrlEntity> processUrlEntityToList = processUrlDao.findByProgramCode(programEntityTo.getCode());

		// TODO 移动后，processUrlEntityFromList就没法通过programCode搜索到了
		Assert.isTrue(processUrlEntityFromList.size() > 0, "没有移动来源数据");
//		Assert.isTrue(processUrlEntityToList.size() > 0, "没有移动目标数据");

		for (ProcessUrlEntity processUrlEntityFrom : processUrlEntityFromList) {
//			ProcessUrlEntity processUrlEntityTo = processUrlEntityToList.get(0);
//			Assert.isTrue(processUrlEntityFrom.getType().equals(processUrlEntityTo.getType()), "移动数据的type必须相同");

			// 移动数据
			Map<String, String> newTags = new HashMap<>();
			newTags.put("名字", programEntityTo.getFullName());

			Map<String, String> tags = new HashMap<>();
			tags.put("网站", processUrlEntityFrom.getSourceKey());
			tags.put("类型", processUrlEntityFrom.getType());

			// 移动URL名字
			tags.put("名字", processUrlEntityFrom.getName());
			if (XStringUtils.isBlank(tags.get("名字"))) {
				throw new XInfluxdbException("tag名字不能为空");
			}
			if (XStringUtils.isBlank(newTags.get("名字"))) {
				throw new XInfluxdbException("tag名字不能为空");
			}
			influxdbAction.copyDataAndDrop("play_data", MPlayData.class, tags, newTags);

			// 移动节目中的名字 TODO 这里会移动多次，怎么把他放到循环外面去
			tags.put("名字", programEntityFrom.getFullName());
			if (XStringUtils.isBlank(tags.get("名字"))) {
				throw new XInfluxdbException("tag名字不能为空");
			}
			if (XStringUtils.isBlank(newTags.get("名字"))) {
				throw new XInfluxdbException("tag名字不能为空");
			}
			influxdbAction.copyDataAndDrop("play_data", MPlayData.class, tags, newTags);

			// 修改当前Program信息
			processUrlEntityFrom.setProgramCode(programEntityTo.getCode());
			processUrlDao.save(processUrlEntityFrom);


			// TODO 节目下所有的剧集也需要移动
		}

	}

	public void renameProgram(String id, String newName, String newDivitionName, String newTitle) {

	}

	public void renameEpisode(String id, String newName, String newDivitionName, String newTitle) {

	}

	public void setProgram2Url(String programId, String processUrlId) {

	}

	public void setEpisode2Url(String episodeId, String processUrlId) {

	}

	/**
	 * 相同measurement拷贝InfluxDB数据
	 *
	 * @param site      site
	 * @param beginDate beginDate
	 * @param endDate   endDate
	 * @param nowName   nowName
	 * @param newName   newName
	 */
	public void copyInfluxData(String site, Date beginDate, Date endDate, String nowName, String newName) {

	}
}
