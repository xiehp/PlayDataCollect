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
		// 开始时间始终取自页面， 不自动生成
		if (processUrlEntity.getBeginDate() == null && beginDate != null) {
			processUrlEntity.setBeginDate(beginDate);
		}
		// 重新发布时间，先尝试获取页面时间
		if (beginDate != null) {
			processUrlEntity.setReBeginDate(beginDate);
		}
		// 重新发布时间，如果页面不存在，则使用当前时间
		if (processUrlEntity.getReBeginDate() == null) {
			processUrlEntity.setReBeginDate(new Date());
		}
		processUrlEntity.setUrl(url);
		processUrlEntity.setParams(XJsonUtil.toJsonString(params));
		processUrlEntity.setRemark(desc);

		save(processUrlEntity);
	}
}
