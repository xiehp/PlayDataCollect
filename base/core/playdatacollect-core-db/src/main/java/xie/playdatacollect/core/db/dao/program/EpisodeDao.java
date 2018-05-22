package xie.playdatacollect.core.db.dao.program;

import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.entity.program.EpisodeEntity;

public interface EpisodeDao extends BaseDao<EpisodeEntity, String> {

	EpisodeEntity findByFullName(String name);
}
