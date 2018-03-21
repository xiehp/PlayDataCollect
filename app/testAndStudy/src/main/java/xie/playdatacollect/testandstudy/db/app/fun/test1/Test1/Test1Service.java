package xie.playdatacollect.testandstudy.db.app.fun.test1.Test1;


import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.jpa.service.BaseService;

import javax.annotation.Resource;

@Service
public class Test1Service extends BaseService<Test1Entity, String> {

	@Resource
	Test1Dao test1Dao;

	@Override
	public BaseDao<Test1Entity, String> getBaseDao() {
		return test1Dao;
	}
}
