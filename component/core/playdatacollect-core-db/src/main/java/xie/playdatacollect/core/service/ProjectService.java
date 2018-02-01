package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.core.dao.ProjectDao;
import xie.playdatacollect.core.entity.ProjectEntity;

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
