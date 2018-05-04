package xie.playdatacollect.core.db.service;

import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.utils.XJsonUtil;
import xie.playdatacollect.core.db.dao.ProcessUrlDao;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class ProcessUrlService extends BasePlayCollectService<ProcessUrlEntity, String> {

	@Resource
	private ProcessUrlDao processUrlDao;

	@Override
	public BaseDao<ProcessUrlEntity, String> getBaseDao() {
		return processUrlDao;
	}

	public void saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate) {
		saveProcessUrlData(sourceKey, name, type, desc, url, beginDate, null);
	}

	public void saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate, Map<String, Object> params) {
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
		processUrlEntity.setParams(XJsonUtil.toJsonString(params));
		processUrlEntity.setRemark(desc);

		save(processUrlEntity);
	}
}
