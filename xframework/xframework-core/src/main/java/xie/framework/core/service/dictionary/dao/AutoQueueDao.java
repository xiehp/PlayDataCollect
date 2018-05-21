package xie.framework.core.service.dictionary.dao;

import xie.common.spring.jpa.repository.BaseDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;

public interface AutoQueueDao extends BaseDao<AutoQueueEntity, String> {

	AutoQueueEntity findByCode(String code);
}
