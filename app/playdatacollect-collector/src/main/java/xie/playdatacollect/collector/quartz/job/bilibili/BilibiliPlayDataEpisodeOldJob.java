package xie.playdatacollect.collector.quartz.job.bilibili;

import org.springframework.data.domain.Page;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 搜索开始时间小于指定时间的数据 */
public class BilibiliPlayDataEpisodeOldJob extends BilibiliPlayDataEpisodeJob {

	@Override
	protected List<ProcessUrlEntity> getProcessUrlList() {
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("EQ_sourceKey", PlayDataConst.SOURCE_KEY_BILIBILI);
		searchMap.put("EQ_type", PlayDataConst.SOURCE_TYPE_EPISODE);
		searchMap.put("LT_beginDate", Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()));

		Page<ProcessUrlEntity> page = allServiceUtil.getProcessUrlService().searchPageByParams(searchMap, ProcessUrlEntity.class);
		List<ProcessUrlEntity> list = page.getContent();
		return list;
	}

}
