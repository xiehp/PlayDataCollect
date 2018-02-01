package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.base.db.service.BaseService;
import xie.playdatacollect.core.dao.TagDao;
import xie.playdatacollect.core.entity.TagEntity;

import javax.annotation.Resource;

@Service
public class TagService extends BasePlayCollectService<TagEntity, String> {

	@Resource
	private TagDao baseDao;

	@Override
	public BaseDao<TagEntity, String> getBaseDao() {
		return baseDao;
	}
}
