package xie.playdatacollect.core.db.service.program;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.playdatacollect.core.db.dao.MetricDao;
import xie.playdatacollect.core.db.dao.program.SeriesDao;
import xie.playdatacollect.core.db.entity.program.SeriesEntity;
import xie.playdatacollect.core.db.entity.tsd.MetricEntity;
import xie.playdatacollect.core.db.service.BasePlayCollectService;

import javax.annotation.Resource;

@Service
public class SeriesService extends BasePlayCollectService<SeriesEntity, String> {

	@Resource
	private SeriesDao seriesDao;

	@Override
	public BaseDao<SeriesEntity, String> getBaseDao() {
		return seriesDao;
	}
}
