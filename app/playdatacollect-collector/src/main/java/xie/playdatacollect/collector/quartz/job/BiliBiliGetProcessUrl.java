package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.Spider;
import xie.common.date.DateUtil;
import xie.common.number.XNumberUtils;
import xie.module.log.XLog;
import xie.playdatacollect.collector.process.ProcessBilibili;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.utils.AllDaoUtil;
import xie.playdatacollect.core.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliAnimePageProcessor;
import xie.playdatacollect.spider.webmagic.processor.bilibili.BilibiliNewYear2018Processor;

import javax.annotation.Resource;
import java.util.*;

public class BiliBiliGetProcessUrl extends QuartzJobBean {

	public static final String TIMELINE_JP = "http://bangumi.bilibili.com/web_api/timeline_global";
	public static final String TIMELINE_CN = "http://bangumi.bilibili.com/web_api/timeline_cn";

	@Resource
	AllDaoUtil allDaoUtil;
	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	ProcessBilibili processBilibili;

	@Resource
	RestTemplate restTemplate;

	private String name;

	/**
	 * Invoked if a Job data map entry with that name<br>
	 * 通过job的参数自动塞入数值
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {
		XLog.info(this, DateUtil.convertToString(new Date()) + this.getClass().getName() + " start", name);

		processResult(TIMELINE_JP);
		processResult(TIMELINE_CN);

		XLog.info(this, DateUtil.convertToString(new Date()) + this.getClass().getName() + " end", name);
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
				long pub_ts = XNumberUtils.getLongValue(seasons.getOrDefault("pub_ts", 0L)) * 1000;
				int season_id = (int) seasons.getOrDefault("season_id", 0);
				int ep_id = (int) seasons.getOrDefault("ep_id", 0);
				int is_published = (int) seasons.getOrDefault("is_published", 1);
				String cover = (String) seasons.getOrDefault("cover", ""); // 封面图片URL地址
				String square_cover = (String) seasons.getOrDefault("square_cover", "");
				String title = (String) seasons.getOrDefault("title", "");
				String pub_index = (String) seasons.getOrDefault("pub_index", "");

				// TODO 将未发布的剧集存入到URL处理列表中
				if (is_published == 0) {

				}

				// TODO 将数据存入已处理标记列表


				// TODO 将节目存入到URL处理列表中
				allServiceUtil.getProcessUrlService().saveProcessUrlData(
						PlayDataConst.SOURCE_KEY_BILIBILI,
						title,
						"program",
						null,
						"https://bangumi.bilibili.com/anime/" + season_id,
						new Date(pub_ts));
			}
		}
	}

}
