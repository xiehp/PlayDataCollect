package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.dao.MetricDao;
import xie.playdatacollect.core.entity.tsd.MetricEntity;

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
