package xie.playdatacollect.core.dao;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;

import java.util.List;

public interface ProcessUrlDao extends BaseDao<ProcessUrlEntity, String> {

	ProcessUrlEntity findBySourceKeyAndName(String sourceKey, String name);

	ProcessUrlEntity findByName(String name);

	ProcessUrlEntity findByUrl(String url);

	List<ProcessUrlEntity> findByType(String type);

	List<ProcessUrlEntity> findBySourceKeyAndType(String sourceKey, String name);


	//	/** 搜索开始时间小于指定时间的数据 */
	//	List<ProcessUrlEntity> findByTypeAndBeginDateLessThan(String type, Date beginDate);
	//	/** 搜索开始时间小于当前时间并且大于指定时间的数据 */
	//	List<ProcessUrlEntity> findByTypeAndBeginDateLessThanEqualAndBeginDateGreaterThanEqual(String type, Date beginDate);
}
