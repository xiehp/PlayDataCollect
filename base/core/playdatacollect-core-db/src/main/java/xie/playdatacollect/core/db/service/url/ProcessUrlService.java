package xie.playdatacollect.core.db.service.url;

import org.springframework.data.convert.JodaTimeConverters;
import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.constant.XConst;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.dao.program.ProgramDao;
import xie.playdatacollect.core.db.dao.url.ProcessUrlDao;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.service.BasePlayCollectService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Service
public class ProcessUrlService extends BasePlayCollectService<ProcessUrlEntity, String> {

	@Resource
	private ProcessUrlDao processUrlDao;
	@Resource
	private ProgramDao programDao;

	@Override
	public BaseDao<ProcessUrlEntity, String> getBaseDao() {
		return processUrlDao;
	}

	/**
	 * 保存或更新抓取的URL信息
	 */
	public ProcessUrlEntity saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate) {
		return saveProcessUrlData(sourceKey, name, type, desc, url, beginDate, null);
	}

	/**
	 * 保存或更新抓取的URL信息
	 */
	public ProcessUrlEntity saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate, Map<String, Object> params) {

		name = name.trim();
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

		// 最近发布时间
		// 如果存在再发布时间，则比对当前时间，大的话减去1周，否则直接设置
		if (processUrlEntity.getBeginDate() != null) {
			processUrlEntity.setRecentBeginDate(processUrlEntity.getBeginDate());
		}
		if (processUrlEntity.getReBeginDate() != null) {
			processUrlEntity.setRecentBeginDate(processUrlEntity.getReBeginDate());
		}
		if (processUrlEntity.getRecentBeginDate() != null) {
			if (processUrlEntity.getRecentBeginDate().after(new Date())) {
				processUrlEntity.setRecentBeginDate(new Date(processUrlEntity.getRecentBeginDate().getTime() - XConst.SECOND_07_DAY * 1000));
			}
		}

		processUrlEntity.setUrl(url);
		processUrlEntity.setParams(XJsonUtil.toJsonString(params));
		processUrlEntity.setRemark(desc);

		processUrlEntity = save(processUrlEntity);
		return processUrlEntity;
	}
}
