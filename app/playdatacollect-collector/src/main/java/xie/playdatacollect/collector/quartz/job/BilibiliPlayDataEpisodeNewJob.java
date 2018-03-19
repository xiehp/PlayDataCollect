package xie.playdatacollect.collector.quartz.job;

import org.springframework.data.domain.Page;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 搜索开始时间小于当前时间并且大于指定时间的数据 */
public class BilibiliPlayDataEpisodeNewJob extends BilibiliPlayDataEpisodeJob {

	@Override
	protected List<ProcessUrlEntity> getProcessUrlList() {
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("EQ_type", "episode");
		searchMap.put("GTE_beginDate", Date.from(LocalDateTime.now().minusDays(2).atZone(ZoneId.systemDefault()).toInstant()));
		searchMap.put("LTE_beginDate", Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant()));

		Page<ProcessUrlEntity> page = allServiceUtil.getProcessUrlService().searchPageByParams(searchMap, ProcessUrlEntity.class);
		List<ProcessUrlEntity> list = page.getContent();
		return list;
	}

}
