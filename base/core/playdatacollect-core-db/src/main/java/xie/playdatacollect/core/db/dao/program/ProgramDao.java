package xie.playdatacollect.core.db.dao.program;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;

public interface ProgramDao extends BaseDao<ProgramEntity, String> {

	ProgramEntity findByFullName(String name);

	ProgramEntity findByCode(Long programCode);
}
