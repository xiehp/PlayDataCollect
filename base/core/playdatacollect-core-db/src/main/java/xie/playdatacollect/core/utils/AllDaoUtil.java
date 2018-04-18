package xie.playdatacollect.core.utils;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.dao.MarkRecordDao;
import xie.playdatacollect.core.dao.ProcessUrlDao;
import xie.playdatacollect.core.dao.SourcesDao;
import xie.playdatacollect.core.dao.schedule.ScheduleJobDao;
import xie.playdatacollect.core.dao.schedule.ScheduleTriggerDao;

import javax.annotation.Resource;

@Component
public class AllDaoUtil {
	@Resource
	private SourcesDao sourcesDao;
	@Resource
	private ProcessUrlDao processUrlDao;
	@Resource
	private MarkRecordDao markRecordDao;
	@Resource
	private ScheduleJobDao scheduleJobDao;
	@Resource
	private ScheduleTriggerDao scheduleTriggerDao;

	public SourcesDao getSourcesDao() {
		return sourcesDao;
	}

	public ProcessUrlDao getProcessUrlDao() {
		return processUrlDao;
	}

	public MarkRecordDao getMarkRecordDao() {
		return markRecordDao;
	}

	public ScheduleJobDao getScheduleJobDao() {
		return scheduleJobDao;
	}

	public ScheduleTriggerDao getScheduleTriggerDao() {
		return scheduleTriggerDao;
	}
}
