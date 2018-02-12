package xie.playdatacollect.core.dao;

import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.core.entity.ProcessUrlEntity;

public interface ProcessUrlDao extends BaseDao<ProcessUrlEntity, String> {

	ProcessUrlEntity findByName(String name);
	ProcessUrlEntity findByUrl(String url);
}
