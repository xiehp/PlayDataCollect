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
	 *
	 * @param  beginDate 页面上的开始时间，请不要由程序自动生成
	 */
	public ProcessUrlEntity saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate) {
		return saveProcessUrlData(sourceKey, name, type, desc, url, beginDate, null);
	}

	/**
	 * 保存或更新抓取的URL信息
	 *
	 * @param  beginDate 页面上的开始时间，请不要由程序自动生成
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

		// 重新发布时间，每次更新当前页面时间
		if (beginDate != null) {
			Date reBeginDate = processUrlEntity.getReBeginDate();
			if (reBeginDate == null || !reBeginDate.equals(beginDate)) {
				processUrlEntity.setReBeginDate(beginDate);

				// 设置最近发布时间
				if (reBeginDate == null) {
					processUrlEntity.setRecentBeginDate(processUrlEntity.getReBeginDate());
				} else {
					processUrlEntity.setRecentBeginDate(reBeginDate);
				}
			}
		}

		// 页面上无法传日期过来，则设置最近发布时间为当前时间
		if (processUrlEntity.getBeginDate() == null && processUrlEntity.getReBeginDate() == null && processUrlEntity.getRecentBeginDate() == null) {
			processUrlEntity.setRecentBeginDate(new Date());
		}

		processUrlEntity.setUrl(url);
		processUrlEntity.setParams(XJsonUtil.toJsonString(params));
		processUrlEntity.setRemark(desc);

		processUrlEntity = save(processUrlEntity);
		return processUrlEntity;
	}
}
