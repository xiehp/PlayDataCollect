package xie.playdatacollect.core.service;

import org.springframework.stereotype.Service;
import xie.playdatacollect.base.db.repository.BaseDao;
import xie.playdatacollect.core.dao.ProcessUrlDao;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ProcessUrlService extends BasePlayCollectService<ProcessUrlEntity, String> {

	@Resource
	private ProcessUrlDao processUrlDao;

	@Override
	public BaseDao<ProcessUrlEntity, String> getBaseDao() {
		return processUrlDao;
	}

	public void saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate) {
		ProcessUrlEntity processUrlEntity = processUrlDao.findBySourceKeyAndName(sourceKey, name);
		if (processUrlEntity == null) {
			processUrlEntity = new ProcessUrlEntity();
		}

		processUrlEntity.setSourceKey(sourceKey);
		processUrlEntity.setName(name);
		processUrlEntity.setType(type);
		if (beginDate != null) {
			processUrlEntity.setBeginDate(beginDate);
		}
		processUrlEntity.setUrl(url);
		processUrlEntity.setRemark(desc);

		save(processUrlEntity);
	}
}
