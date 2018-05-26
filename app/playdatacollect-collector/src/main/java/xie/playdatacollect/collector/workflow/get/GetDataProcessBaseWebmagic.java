package xie.playdatacollect.collector.workflow.get;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class GetDataProcessBaseWebmagic implements IGetDataProcess {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<CollectedData> getDataList(List<ProcessUrlEntity> listProcessUrl, JobDataMap jobDataMap) {

		List<String> listUrl = new ArrayList<>();
		listProcessUrl.forEach((processUrl) -> {
			if (processUrl.getUrl() != null) {
				listUrl.add(processUrl.getUrl());
			} else {
				logger.warn("{} 的url为空，无法获取数据。", processUrl.getName());
			}
		});

		// 生成url和名字对应map，如果基础信息中也存在，则替换名字
		Map<String, String> url2NameMap = listProcessUrl.stream()
				.collect(Collectors.toMap(
						ProcessUrlEntity::getUrl,
						ProcessUrlEntity::getInfluxdbName,
						(oldValue, newValue) -> {
							logger.warn(String.format("生成url和名字对应map, Duplicate key , oldValue:%s, , newValue:%s", oldValue, newValue));
							return newValue;
						})
				);


		PageProcessor pageProcessor = getPageProcessor(url2NameMap);
		Spider spider = Spider.create(pageProcessor).thread(3);
		List<CollectedData> list = spiderGetAll(spider, listUrl);
		return list;
	}

	public abstract List<CollectedData> spiderGetAll(Spider spider, List<String> listUrl);

	public abstract PageProcessor getPageProcessor(Map<String, String> url2NameMap);
}
