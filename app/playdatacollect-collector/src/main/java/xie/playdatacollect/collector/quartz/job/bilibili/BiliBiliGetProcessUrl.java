package xie.playdatacollect.collector.quartz.job.bilibili;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xie.common.utils.number.XNumberUtils;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.utils.AllServiceUtil;
import xie.playdatacollect.core.db.utils.InitEntityDataRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BiliBiliGetProcessUrl extends XBaseQuartzJobBean {

	public static final String TIMELINE_JP = "http://bangumi.bilibili.com/web_api/timeline_global";
	public static final String TIMELINE_CN = "http://bangumi.bilibili.com/web_api/timeline_cn";

	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	RestTemplate restTemplate;

	@Resource
	InitEntityDataRunner initEntityDataRunner;

	@Override
	protected void executeJob(JobExecutionContext context) {
//		try {
//			initEntityDataRunner.run(null);
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
		processResult(TIMELINE_JP);
		processResult(TIMELINE_CN);
	}

	private void processResult(String url) {
		Map map = restTemplate.getForObject(url, HashMap.class);
		Integer code = (Integer) map.getOrDefault("code", -1);
		String message = (String) map.getOrDefault("message", "");

		List<Map> resultList = (List<Map>) map.get("result");
		for (Map resultMap : resultList) {

			long date_ts = XNumberUtils.getLongValue(resultMap.getOrDefault("date_ts", 0L)) * 1000;
			int is_today = (int) resultMap.getOrDefault("is_today", 0);

			List<Map> seasonsList = (List<Map>) resultMap.get("seasons");

			for (Map seasons : seasonsList) {
				// 发布时间
				long pub_ts = XNumberUtils.getLongValue(seasons.getOrDefault("pub_ts", 0L)) * 1000;
				// 节目ID
				int season_id = (int) seasons.getOrDefault("season_id", 0);
				// 剧集ID
				int ep_id = (int) seasons.getOrDefault("ep_id", 0);
				// 是否发布 0:未发布 1:已发布
				int is_published = (int) seasons.getOrDefault("is_published", 1);
				String cover = (String) seasons.getOrDefault("cover", ""); // 封面图片URL地址
				String square_cover = (String) seasons.getOrDefault("square_cover", "");
				String title = (String) seasons.getOrDefault("title", "");
				String pub_index = (String) seasons.getOrDefault("pub_index", "");

				// TODO 将数据存入已处理标记列表


				// 将节目存入到URL处理列表中
				allServiceUtil.getProcessUrlService().saveProcessUrlData(
						PlayDataConst.SOURCE_KEY_BILIBILI,
						title,
						PlayDataConst.SOURCE_TYPE_PROGRAM,
						null,
						"https://bangumi.bilibili.com/anime/" + season_id,
						null
				);

				// 将未发布的剧集存入到URL处理列表中
				if (is_published == 0) {
					allServiceUtil.getProcessUrlService().saveProcessUrlData(
							PlayDataConst.SOURCE_KEY_BILIBILI,
							title + " " + pub_index,
							PlayDataConst.SOURCE_TYPE_EPISODE,
							null,
							"https://www.bilibili.com/bangumi/play/ep" + ep_id,
							new Date(pub_ts)
					);
				} else {
					allServiceUtil.getProcessUrlService().saveProcessUrlData(
							PlayDataConst.SOURCE_KEY_BILIBILI,
							title + " " + pub_index,
							PlayDataConst.SOURCE_TYPE_EPISODE,
							null,
							"https://www.bilibili.com/bangumi/play/ep" + ep_id,
							new Date(pub_ts)
					);
				}
			}
		}
	}

}
