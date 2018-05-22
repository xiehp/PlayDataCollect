package xie.playdatacollect.core.db.service.program;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.framework.core.service.dictionary.utils.AutoQueueUtils;
import xie.playdatacollect.core.db.dao.MetricDao;
import xie.playdatacollect.core.db.dao.program.EpisodeDao;
import xie.playdatacollect.core.db.entity.program.EpisodeEntity;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.entity.tsd.MetricEntity;
import xie.playdatacollect.core.db.service.BasePlayCollectService;

import javax.annotation.Resource;

@Service
public class EpisodeService extends BasePlayCollectService<EpisodeEntity, String> {

	@Resource
	private EpisodeDao baseDao;

	@Resource
	private AutoQueueUtils autoQueueUtils;

	@Override
	public BaseDao<EpisodeEntity, String> getBaseDao() {
		return baseDao;
	}

	public EpisodeEntity createNew(String fullName) {
		EpisodeEntity episodeEntity  = new EpisodeEntity();
		episodeEntity.setName(fullName);
		episodeEntity.setCode(autoQueueUtils.nextNumber("EpisodeEntity"));

		return save(episodeEntity);
	}
}
