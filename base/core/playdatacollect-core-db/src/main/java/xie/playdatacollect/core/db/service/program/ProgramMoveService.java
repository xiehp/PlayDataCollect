package xie.playdatacollect.core.db.service.program;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.dao.MetricDao;
import xie.playdatacollect.core.db.dao.program.ProgramMoveDao;
import xie.playdatacollect.core.db.entity.program.ProgramMoveEntity;
import xie.playdatacollect.core.db.entity.tsd.MetricEntity;
import xie.playdatacollect.core.db.service.BasePlayCollectService;

import javax.annotation.Resource;

@Service
public class ProgramMoveService extends BasePlayCollectService<ProgramMoveEntity, String> {

	@Resource
	private ProgramMoveDao baseDao;

	@Override
	public BaseDao<ProgramMoveEntity, String> getBaseDao() {
		return baseDao;
	}
}
