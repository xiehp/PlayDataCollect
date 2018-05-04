package xie.playdatacollect.core.db.service;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.dao.ProjectDao;
import xie.playdatacollect.core.db.entity.ProjectEntity;

import javax.annotation.Resource;

@Service
public class ProjectService extends BasePlayCollectService<ProjectEntity, String> {

	@Resource
	private ProjectDao baseDao;

	@Override
	public BaseDao<ProjectEntity, String> getBaseDao() {
		return baseDao;
	}
}
