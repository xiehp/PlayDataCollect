package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.entity.BaseEntity;
import xie.playdatacollect.base.db.service.BaseService;
import xie.playdatacollect.core.dao.BasePlayCollectDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public abstract class BasePlayCollectService<M extends BaseEntity, ID extends Serializable> extends BaseService<M, ID> {

	public M findByKey(String key) {
		return (M) ((BasePlayCollectDao) getBaseDao()).findByKey(key);
	}

	public M insertNewKeyName(String key, String name, Class<M> clazz) throws IllegalAccessException, InstantiationException {
		M entity = findByKey(key);
		if (entity == null) {
			entity = clazz.newInstance();
		}

		Map<String, String> map = new HashMap<>();
		map.put("key", key);
		map.put("name", name);
		entity.copyFrom(map);
		entity = save(entity);

		logging.info("insertNewKeyName: {}", entity.copyTo(map));
		System.out.println();
		return entity;
	}
}
