package xie.playdatacollect.testandstudy.db.app.fun.test1.Test1;


import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseRepository;
import xie.playdatacollect.base.db.service.BaseService;

import javax.annotation.Resource;

@Service
public class Test1Service extends BaseService<Test1Entity, String> {

	@Resource
	Test1Dao test1Dao;

	@Override
	public BaseRepository<Test1Entity, String> getBaseRepository() {
		return test1Dao;
	}
}
