package xie.playdatacollect.core.db.dao.schedule;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.entity.schedule.ScheduleTriggerEntity;

import java.util.List;

public interface ScheduleTriggerDao extends BaseDao<ScheduleTriggerEntity, String> {

	ScheduleTriggerEntity findByIdentityAndGroupsAndVersionName(String identity, String group, String versionName);
	List<ScheduleTriggerEntity> findByVersionName(String versionName);
}
