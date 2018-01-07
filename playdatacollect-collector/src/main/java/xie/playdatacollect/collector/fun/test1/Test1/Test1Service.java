package xie.playdatacollect.collector.fun.test1.Test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseRepository;
import xie.playdatacollect.base.db.service.BaseService;

@Service
public class Test1Service extends BaseService<Test1Entity, String> {

	@Autowired
	Test1Dao test1Dao;

	@Override
	public BaseRepository<Test1Entity, String> getBaseRepository() {
		return test1Dao;
	}
}
