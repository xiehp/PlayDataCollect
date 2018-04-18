package xie.playdatacollect.core.dao.schedule;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.entity.schedule.ScheduleJobEntity;
import xie.playdatacollect.core.entity.schedule.ScheduleTriggerEntity;

import java.util.List;

public interface ScheduleTriggerDao extends BaseDao<ScheduleTriggerEntity, String> {

	ScheduleTriggerEntity findByIdentityAndGroupsAndVersionName(String identity, String group, String versionName);
	List<ScheduleTriggerEntity> findByVersionName(String versionName);
}
