package xie.playdatacollect.core.service;


import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseRepository;
import xie.playdatacollect.base.db.service.BaseService;
import xie.playdatacollect.core.dao.ProjectDao;
import xie.playdatacollect.core.entity.ProjectEntity;

import javax.annotation.Resource;

@Service
public class ProjectService extends BaseService<ProjectEntity, String> {

	@Resource
	ProjectDao projectDao;

	@Override
	public BaseRepository<ProjectEntity, String> getBaseRepository() {
		return projectDao;
	}
}
