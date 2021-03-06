package xie.playdatacollect.core.db.dao.schedule;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.entity.schedule.ScheduleJobEntity;

import java.util.List;

public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity, String> {

	ScheduleJobEntity findByIdentityAndGroupsAndVersionName(String identity, String group, String versionName);
	List<ScheduleJobEntity> findByVersionName(String versionName);
}
