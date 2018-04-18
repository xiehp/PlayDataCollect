package xie.framework.core.service.dictionary.dao;

import org.springframework.data.domain.Sort;
import xie.common.spring.jpa.repository.BaseDao;
import xie.framework.core.service.dictionary.entity.PublicDictionary;

import java.util.List;

public interface PublicDictionaryDao extends BaseDao<PublicDictionary, String> {

	List<PublicDictionary> findByTypeId(String typeId, Sort sort);

}
