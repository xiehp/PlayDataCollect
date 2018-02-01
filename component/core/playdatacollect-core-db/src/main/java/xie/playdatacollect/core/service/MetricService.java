package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.core.dao.MetricDao;
import xie.playdatacollect.core.entity.MetricEntity;

import javax.annotation.Resource;

@Service
public class MetricService extends BasePlayCollectService<MetricEntity, String> {

	@Resource
	private MetricDao baseDao;

	@Override
	public BaseDao<MetricEntity, String> getBaseDao() {
		return baseDao;
	}
}
