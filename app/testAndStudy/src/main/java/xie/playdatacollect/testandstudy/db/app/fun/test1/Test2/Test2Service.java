package xie.playdatacollect.testandstudy.db.app.fun.test1.Test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.jpa.service.BaseService;

@Service
public class Test2Service extends BaseService<Test2Entity, String> {

	@Autowired
	Test2Dao test2Dao;

	@Override
	public BaseDao<Test2Entity, String> getBaseDao() {
		return test2Dao;
	}
}
