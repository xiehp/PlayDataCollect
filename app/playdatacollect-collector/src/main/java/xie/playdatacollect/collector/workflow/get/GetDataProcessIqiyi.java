package xie.playdatacollect.collector.workflow.get;

import org.quartz.JobDataMap;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.string.XStringUtils;
import xie.common.utils.utils.XRegExpUtils;
import xie.playdatacollect.collector.workflow.use.UseDataProcess;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataFactory;
import xie.playdatacollect.common.utils.PlayDataUtils;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.utils.AllDaoUtil;
import xie.playdatacollect.core.db.utils.AllServiceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class GetDataProcessIqiyi implements IGetDataProcess {
	@Resource
	AllDaoUtil allDaoUtil;
	@Resource
	AllServiceUtil allServiceUtil;

	@Resource
	UseDataProcess useDataProcess;
	@Resource
	RestTemplate restTemplate;

	@Override
	public List<CollectedData> getDataList(List<ProcessUrlEntity> listProcessUrl, JobDataMap jobDataMap) {
		List<String> listURL = new ArrayList<>();
		listProcessUrl.forEach((processUrl) -> {
			if (XStringUtils.isNotBlank(processUrl.getParams())) {
				Map<String, Object> map = XJsonUtil.fromJsonString(processUrl.getParams());
				if (XStringUtils.isNotBlank((String) map.get("tvId"))) {
					return;
				}
			}

			listURL.add(processUrl.getUrl());
		});

		List<CollectedData> collectedDataList = new ArrayList<>();
		String url = "http://mixer.video.iqiyi.com/jp/mixin/videos/";
		long time = new Date().getTime();
		CollectedDataFactory factory = new CollectedDataFactory(time);
		listProcessUrl.forEach((processUrl) -> {
			if (XStringUtils.isNotBlank(processUrl.getParams())) {
				Map<String, Object> map = XJsonUtil.fromJsonString(processUrl.getParams());
				String tvId = (String) map.get("tvId");
				if (XStringUtils.isNotBlank(tvId)) {
					// 获取数据
					String resultStr = restTemplate.getForObject(url + tvId, String.class);
					String resultJsonStr = XRegExpUtils.findOnceAndFirstGroup(resultStr, "(\\{.*\\})");
					Map<String, Object> result = XJsonUtil.fromJsonString(resultJsonStr);
					Long albumId = PlayDataUtils.parseValue(result.get("albumId"));
					String albumName = (String) result.get("albumName");

					Long playCount = PlayDataUtils.parseValue(result.get("playCount"));
					Double score = PlayDataUtils.parseDoubleValue(result.get("score"));

					CollectedData collectedData = factory.create();
					collectedData.setSite(processUrl.getSourceKey());
					collectedData.setType(processUrl.getType());
					collectedData.setName(processUrl.getInfluxdbName());
					collectedData.setPlayCount(playCount);
					collectedData.setScore(score);
//					collectedData.addExtendData("albumId", albumId);
//					collectedData.addCNName("albumId", "albumId");
					collectedDataList.add(collectedData);
				}
			}
		});

		return collectedDataList;
	}
}
