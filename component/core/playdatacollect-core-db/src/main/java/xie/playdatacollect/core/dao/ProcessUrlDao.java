package xie.playdatacollect.core.dao;

import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;

import java.util.Date;
import java.util.List;

public interface ProcessUrlDao extends BaseDao<ProcessUrlEntity, String> {

	ProcessUrlEntity findBySourceKeyAndName(String sourceKey, String name);
	ProcessUrlEntity findByName(String name);
	ProcessUrlEntity findByUrl(String url);

	List<ProcessUrlEntity> findByType(String type);
//	/** 搜索开始时间小于指定时间的数据 */
//	List<ProcessUrlEntity> findByTypeAndBeginDateLessThan(String type, Date beginDate);
//	/** 搜索开始时间小于当前时间并且大于指定时间的数据 */
//	List<ProcessUrlEntity> findByTypeAndBeginDateLessThanEqualAndBeginDateGreaterThanEqual(String type, Date beginDate);
}
