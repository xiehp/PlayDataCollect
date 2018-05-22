package xie.playdatacollect.core.db.service.program;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.framework.core.service.dictionary.utils.AutoQueueUtils;
import xie.playdatacollect.core.db.dao.MetricDao;
import xie.playdatacollect.core.db.dao.program.ProgramDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.entity.tsd.MetricEntity;
import xie.playdatacollect.core.db.service.BasePlayCollectService;

import javax.annotation.Resource;

@Service
public class ProgramService extends BasePlayCollectService<ProgramEntity, String> {

	@Resource
	private ProgramDao baseDao;

	@Resource
	private AutoQueueUtils autoQueueUtils;

	@Override
	public BaseDao<ProgramEntity, String> getBaseDao() {
		return baseDao;
	}

	/**
	 * 创建一个空的节目数据
	 *
	 * @param fullName
	 */
	public ProgramEntity createNew(String fullName) {
		ProgramEntity programEntity  = new ProgramEntity();
		programEntity.setFullName(fullName);
		Long code = autoQueueUtils.nextNumber("ProgramEntity");
		programEntity.setCode(code);

		return save(programEntity);
	}
}
