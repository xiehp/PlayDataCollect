package xie.playdatacollect.collector.quartz.job.base;

import org.quartz.JobExecutionContext;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.common.utils.utils.XRegExpUtils;
import xie.framework.core.exception.CodeApplicationException;
import xie.playdatacollect.collector.quartz.job.XBaseQuartzJobBean;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.core.db.utils.AllDaoUtil;
import xie.playdatacollect.core.db.utils.AllServiceUtil;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiDongmanPageProcessor;
import xie.playdatacollect.spider.webmagic.processor.iqiyi.IqiyiProcessUrl;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseGetProcessUrlJob extends XBaseQuartzJobBean {

	@Resource
	private AllDaoUtil allDaoUtil;
	@Resource
	private AllServiceUtil allServiceUtil;
	@Resource
	private RestTemplate restTemplate;

	public abstract String getUrl();

	@Override
	public void executeJob(JobExecutionContext context) {
		runSpider();
	}

	protected void runSpider() {
		Spider spider = Spider.create(new IqiyiDongmanPageProcessor()).thread(2);
		List<ResultItems> resultItemsList = spider.getAll(Arrays.asList(getUrl()));
		for (ResultItems resultItem : resultItemsList) {
			List<IqiyiProcessUrl> list = resultItem.get("list");

			for (IqiyiProcessUrl url : list) {
				try {
					// 根据页面获取其他特定信息
					String episodeUrl = url.getHref();
					String episodeHtml = restTemplate.getForObject(episodeUrl, String.class);

					String tvId = XRegExpUtils.findOnceAndFirstGroup(episodeHtml, "tvId:([0-9]+)");
					if (tvId == null) {
						throw new CodeApplicationException("获取tvId发生问题，" + "tvId:([0-9]+)");
					}

					Map<String, Object> params = new LinkedHashMap<>();
					params.put("tvId", tvId);

					allServiceUtil.getProcessUrlUtils().saveProcessUrlData(
							PlayDataConst.SOURCE_KEY_IQIYI,
							url.getTitle(),
							PlayDataConst.SOURCE_TYPE_PROGRAM,
							"",
							url.getHref(),
							null,
							params
					);
				} catch (Exception e) {
					logger.error("根据页面获取其他特定信息出错", e);
				}
			}
		}
	}

}
