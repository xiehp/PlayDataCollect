package xie.playdatacollect.core.dao;

import org.springframework.data.repository.NoRepositoryBean;
import xie.playdatacollect.base.db.repository.BaseDao;

import java.io.Serializable;

@NoRepositoryBean
public interface BasePlayCollectDao<T, ID extends Serializable> extends BaseDao<T, ID> {

	T findByKeyword(String key);
}
