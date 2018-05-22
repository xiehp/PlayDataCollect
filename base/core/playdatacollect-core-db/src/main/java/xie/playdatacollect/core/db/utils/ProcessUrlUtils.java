package xie.playdatacollect.core.db.utils;

import org.springframework.stereotype.Component;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.dao.program.EpisodeDao;
import xie.playdatacollect.core.db.dao.program.ProgramDao;
import xie.playdatacollect.core.db.entity.program.EpisodeEntity;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.service.program.EpisodeService;
import xie.playdatacollect.core.db.service.program.ProgramService;
import xie.playdatacollect.core.db.service.url.ProcessUrlService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
public class ProcessUrlUtils {

	@Resource
	private ProgramDao programDao;
	@Resource
	private ProgramService programService;
	@Resource
	private EpisodeDao episodeDao;
	@Resource
	private EpisodeService episodeService;
	@Resource
	private ProcessUrlService processUrlService;

	public ProcessUrlEntity saveProcessUrlData(String sourceKey, String name, String type, String desc, String url, Date beginDate, Map<String, Object> params) {

		// 初次生成数据
		ProcessUrlEntity processUrlEntity = processUrlService.saveProcessUrlData(sourceKey, name, type, desc, url, beginDate, params);

		// TODO 判断是否已经关联本地节目信息
		if (PlayDataConst.SOURCE_TYPE_PROGRAM.equalsIgnoreCase(type)) {
			if (processUrlEntity.getProgramCode() == null) {
				// 尝试获取同名本地节目信息
				ProgramEntity programEntity = programDao.findByFullName(name);
				if (programEntity == null) {
					programEntity = programService.createNew(name);
				}
				processUrlEntity.setProgramCode(programEntity.getCode());
				processUrlService.save(processUrlEntity);
			}
		} else if (PlayDataConst.SOURCE_TYPE_EPISODE.equalsIgnoreCase(type) && PlayDataConst.SOURCE_KEY_BILIBILI.equalsIgnoreCase(sourceKey)) {
//			// TODO 剧集暂时只有bilibili弄
//			if (processUrlEntity.getEpisodeCode() == null) {
//				// 尝试获取同名本地节目信息
//				EpisodeEntity episodeEntity = episodeDao.findByFullName(name);
//				if (episodeEntity == null) {
//					episodeEntity = episodeService.createNew(name);
//				}
//				processUrlEntity.setEpisodeCode(episodeEntity.getCode());
//			}
		}

		return processUrlEntity;
	}
}
