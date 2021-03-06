package xie.playdatacollect.collector.workflow.get;

import com.alibaba.druid.sql.visitor.functions.If;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.spider.webmagic.processor.youku.YoukuProgramPageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class GetDataProcessYoukuProgram implements IGetDataProcess {

	@Resource
	RestTemplate restTemplate;

	@Override
	public List<CollectedData> getDataList(List<ProcessUrlEntity> listProcessUrl, JobDataMap jobDataMap) {
		List<String> listURL = new ArrayList<>();
		listProcessUrl.forEach((processUrl) -> {
			if (XStringUtils.isNotBlank(processUrl.getUrl())) {
				listURL.add(processUrl.getUrl());
			}
		});

		// 生成url和名字对应map，如果基础信息中也存在，则替换名字
		Map<String, String> url2NameMap = listProcessUrl.stream()
				.collect(Collectors.toMap(ProcessUrlEntity::getUrl, ProcessUrlEntity::getInfluxdbName));

		// 通过spider获得原始数据
		long dateTime = System.currentTimeMillis();
		Spider spider = Spider.create(new YoukuProgramPageProcessor(dateTime, url2NameMap)).thread(3);
		List<ResultItems> resultItemses = spider.getAll(listURL);

		// 生成自己可处理数据列
		List<CollectedData> collectedDataList = new ArrayList<>();
		for (ResultItems item : resultItemses) {
			collectedDataList.add(item.get("collectedData"));
		}

		return collectedDataList;
	}
}
