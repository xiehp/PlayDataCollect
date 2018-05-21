package xie.framework.core.service.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.jpa.service.BasePageService;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.entity.AutoQueueEntity;


@Service
public class AutoQueueService extends BasePageService<AutoQueueEntity, String> {

	@Autowired
	private AutoQueueDao autoQueueDao;

	@Override
	public BaseDao<AutoQueueEntity, String> getBaseDao() {
		return autoQueueDao;
	}

	public AutoQueueEntity create(String code, long nowNumber, long step) {
		AutoQueueEntity entity = new AutoQueueEntity();
		entity.setCode(code);
		entity.setNowNumber(nowNumber);
		entity.setStepNumber(step);
		return save(entity);
	}

	public String getNowValue(String code) {
		AutoQueueEntity entity = autoQueueDao.findByCode(code);
		if (entity != null) {
			return entity.getNowValue();
		}

		return null;
	}

	public Long getNowNumber(String code) {
		AutoQueueEntity entity = autoQueueDao.findByCode(code);
		if (entity != null) {
			return entity.getNowNumber();
		}

		return 0L;
	}

	public synchronized Long nextNumber(String code) {
		AutoQueueEntity entity = autoQueueDao.findByCode(code);
		if (entity == null) {
			entity = create(code, 0, 1);
		}
		Long nowNumber = entity.getNowNumber();
		Long stepNumber = entity.getStepNumber();
		Long nextNumber = nowNumber + stepNumber;

		entity.setPreNumber(entity.getNowNumber());
		entity.setNowNumber(nextNumber);
		save(entity);
		return nextNumber;
	}

}
