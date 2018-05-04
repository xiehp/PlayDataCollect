package xie.playdatacollect.core.db.dao;

import org.springframework.data.repository.NoRepositoryBean;
import xie.common.spring.jpa.repository.BaseDao;

import java.io.Serializable;

@NoRepositoryBean
public interface BasePlayCollectDao<T, ID extends Serializable> extends BaseDao<T, ID> {

	T findByKeyword(String key);
}
