package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.dao.ValueDao;
import xie.playdatacollect.core.entity.tsd.ValueEntity;

import javax.annotation.Resource;

@Service
public class ValueService extends BasePlayCollectService<ValueEntity, String> {

	@Resource
	private ValueDao baseDao;

	@Override
	public BaseDao<ValueEntity, String> getBaseDao() {
		return baseDao;
	}
}
