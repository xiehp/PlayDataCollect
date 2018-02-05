package xie.playdatacollect.core.service;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.entity.BaseEntity;
import xie.playdatacollect.base.db.service.BaseService;
import xie.playdatacollect.core.dao.BasePlayCollectDao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Service
public abstract class BasePlayCollectService<M extends BaseEntity, ID extends Serializable> extends BaseService<M, ID> {

	public M findByKey(String key) {
		return (M) ((BasePlayCollectDao) getBaseDao()).findByKey(key);
	}

	public M insertNewKeyName(String key, String name, Class<M> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
		M entity = findByKey(key);
		if (entity == null) {
			entity = clazz.newInstance();
		}

		Map<String, String> map = new HashMap<>();
		map.put("key", key);
		map.put("name", name);
		entity.copyFromWithOutBaseInfo(map);
		entity = save(entity);

//		System.out.println("insertNewKeyName: " + entity.toMapWithOutNullAndBase());
		DozerBeanMapper dozerMapper = new DozerBeanMapper();
//		System.out.println(dozerMapper.map(entity, Map.class));
//		System.out.println(entity.toString());
//		System.out.println(entity.toMapWithOutNull());
//		System.out.println(entity.toMapWithOutBase());
//		System.out.println(entity.toMapWithOutNullAndBase());
//		System.out.println();
		return entity;
	}
}
