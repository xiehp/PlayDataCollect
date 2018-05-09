package xie.playdatacollect.collector.quartz.job.iqiyi;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.string.XStringUtils;
import xie.common.utils.utils.XRegExpUtils;
import xie.playdatacollect.collector.quartz.job.base.BasePlayDataProcessUrlJob;
import xie.playdatacollect.collector.workflow.get.GetDataProcessIqiyi;
import xie.playdatacollect.collector.workflow.get.IGetDataProcess;
import xie.playdatacollect.collector.workflow.use.IUseDataProcess;
import xie.playdatacollect.collector.workflow.use.UseDataProcess;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataFactory;
import xie.playdatacollect.common.utils.PlayDataUtils;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;
import xie.playdatacollect.core.db.utils.AllDaoUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class IqiyiPlayDataProgramJob extends BasePlayDataProcessUrlJob {

	@Resource
	AllDaoUtil allDaoUtil;

	@Resource
	UseDataProcess useDataProcess;

	@Resource
	GetDataProcessIqiyi getDataProcessIqiyi;

//	@Override
//	public void executeJob(JobExecutionContext context) {
//		runSpider();
//	}

	@Override
	protected IGetDataProcess getIGetDataProcess() {
		return getDataProcessIqiyi;
	}

	@Override
	protected IUseDataProcess getIUseDataProcess() {
		return useDataProcess;
	}

//	private void runSpider() {
//		List<String> listURL = new ArrayList<>();
//		List<ProcessUrlEntity> list = allDaoUtil.getProcessUrlDao().findBySourceKeyAndType(PlayDataConst.SOURCE_KEY_IQIYI, PlayDataConst.SOURCE_TYPE_PROGRAM);
//		list.forEach((processUrl) -> {
//			if (XStringUtils.isNotBlank(processUrl.getParams())) {
//				Map<String, Object> map = XJsonUtil.fromJsonString(processUrl.getParams());
//				if (XStringUtils.isNotBlank((String) map.get("tvId"))) {
//					return;
//				}
//			}
//
//			listURL.add(processUrl.getUrl());
//		});
//
////		// 通过spider获得原始数据
////		long dateTime = System.currentTimeMillis();
////		Spider spider = Spider.create(new IqiyiPlayPageProcessor(dateTime)).thread(3);
////		List<ResultItems> resultItemses = spider.getAll(listURL);
////
////		// 生成自己可处理数据列
////		List<CollectedData> listData = new ArrayList<>();
////		for (ResultItems item : resultItemses) {
////			listData.add(item.get("collectedData"));
////		}
//
//		List<CollectedData> collectedDataList = new ArrayList<>();
//		String url = "http://mixer.video.iqiyi.com/jp/mixin/videos/";
//		long time = new Date().getTime();
//		CollectedDataFactory factory = new CollectedDataFactory(time);
//		list.forEach((processUrl) -> {
//			if (XStringUtils.isNotBlank(processUrl.getParams())) {
//				Map<String, Object> map = XJsonUtil.fromJsonString(processUrl.getParams());
//				String tvId = (String) map.get("tvId");
//				if (XStringUtils.isNotBlank(tvId)) {
//					// 获取数据
//					String resultStr = restTemplate.getForObject(url + tvId, String.class);
//					String resultJsonStr = XRegExpUtils.findOnceAndFirstGroup(resultStr, "(\\{.*\\})");
//					Map<String, Object> result = XJsonUtil.fromJsonString(resultJsonStr);
//					Long albumId = PlayDataUtils.parseValue(result.get("albumId"));
//					String albumName = (String) result.get("albumName");
//
//					Long playCount = PlayDataUtils.parseValue(result.get("playCount"));
//					Double score = PlayDataUtils.parseDoubleValue(result.get("score"));
//
//					CollectedData collectedData = factory.create();
//					collectedData.setSite(processUrl.getSourceKey());
//					collectedData.setType(processUrl.getType());
//					collectedData.setPlayCount(playCount);
//					collectedData.setName(albumName);
//					collectedData.setScore(score);
////					collectedData.addExtendData("albumId", albumId);
////					collectedData.addCNName("albumId", "albumId");
//					collectedDataList.add(collectedData);
//				}
//			}
//		});
//
//		useDataProcess.use(collectedDataList, time);
//	}

}
