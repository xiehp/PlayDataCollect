package xie.playdatacollect.collector.utils;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.db.service.program.EpisodeService;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.core.db.utils.AllServiceUtil;
import xie.playdatacollect.influxdb.action.XInfluxdbAction;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ProgramMoveUtils {

	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	ProgramService programService;

	@Resource
	EpisodeService episodeService;

	@Resource
	XInfluxdbAction influxdbAction;

	public void moveProgram(String fromId, String toId) {

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
	 * @param site site
	 * @param beginDate beginDate
	 * @param endDate endDate
	 * @param nowName nowName
	 * @param newName newName
	 */
	public void copyInfluxData(String site, Date beginDate, Date endDate, String nowName, String newName) {

	}
}
