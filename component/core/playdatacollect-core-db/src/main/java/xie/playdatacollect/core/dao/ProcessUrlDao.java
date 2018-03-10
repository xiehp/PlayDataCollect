package xie.playdatacollect.core.dao;

import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;

import java.util.List;

public interface ProcessUrlDao extends BaseDao<ProcessUrlEntity, String> {

	ProcessUrlEntity findBySourceKeyAndName(String sourceKey, String name);
	ProcessUrlEntity findByName(String name);
	ProcessUrlEntity findByUrl(String url);

	List<ProcessUrlEntity> findByType(String episode);
}
