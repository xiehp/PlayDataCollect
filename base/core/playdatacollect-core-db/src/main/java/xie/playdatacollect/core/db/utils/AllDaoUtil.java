package xie.playdatacollect.core.db.utils;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.db.dao.MarkRecordDao;
import xie.playdatacollect.core.db.dao.ProcessUrlDao;
import xie.playdatacollect.core.db.dao.SourcesDao;
import xie.playdatacollect.core.db.dao.schedule.ScheduleJobDao;
import xie.playdatacollect.core.db.dao.schedule.ScheduleTriggerDao;

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
