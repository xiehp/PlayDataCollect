package xie.playdatacollect.collector.process;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.log.XLog;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.common.PlayDataConst;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataFactory;
import xie.playdatacollect.common.utils.PlayDataUtils;
import xie.playdatacollect.influxdb.data.CollectedDataInflux;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProcessBilibiliByCollectedData {

	@Resource
	RestTemplate restTemplate;
	Logger logger = XLog.getLog(this);

	public List<CollectedData> spiderGetAll(Spider spider, List<String> listUrl) {

		CollectedDataFactory collectedDataFactory = new CollectedDataFactory(new Date().getTime());
		List<CollectedData> collectedDataList = new ArrayList<>();
		List<ResultItems> resultItemses = spider.getAll(listUrl);
		spider.close();

		List<String> aidList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		List<String> siteList = new ArrayList<>();
		List<Long> 追番人数List = new ArrayList<>();
		List<Long> 承包数List = new ArrayList<>();
		while (true) {
			for (ResultItems resultItemse : resultItemses) {
				try {
					logger.info(resultItemse.getAll().toString());

					String 名字 = resultItemse.getAll().get("名字").toString();
//				int 播放数 = 0;
//				int 追番人数 = 0;
//				int 弹幕总数 = 0;
					Object aid = resultItemse.getAll().get("aid");
					if (aid != null && aid.toString() != null) {
						aid = aid.toString().toLowerCase().replace("av", "");
						if (!aid.toString().matches("\\d+")) {
							aid = "";
						}
					} else {
						aid = "";
					}

					if (XStringUtils.isNotBlank(aid.toString())) {
						siteList.add(PlayDataConst.SOURCE_KEY_BILIBILI); // TODO 参数化
						nameList.add(名字);
						aidList.add(aid.toString());
						追番人数List.add(PlayDataUtils.parseValue(resultItemse.getAll().get("追番人数")));
						承包数List.add(PlayDataUtils.parseValue(resultItemse.getAll().get("承包数")));
					} else {
						CollectedData collectedData = collectedDataFactory.create();
						collectedData.setSite(PlayDataConst.SOURCE_KEY_BILIBILI); // TODO 参数化
						collectedData.setType(PlayDataConst.SOURCE_TYPE_PROGRAM); // TODO 参数化
						collectedData.setName(名字);
//						builder.addField("追番人数", 追番人数);
//						builder.addField("弹幕总数", 弹幕总数);

						collectedData.setPlayCount(PlayDataUtils.parseValue(resultItemse.getAll().get("播放数")));
						collectedData.setShareCount(PlayDataUtils.parseIntegerValue(resultItemse.getAll().get("分享数")));
						collectedData.setCollectCount(PlayDataUtils.parseIntegerValue(resultItemse.getAll().get("收藏数")));
						collectedData.setCommentCount(PlayDataUtils.parseIntegerValue(resultItemse.getAll().get("评论数")));
						// TODO 评分删除不了
						collectedData.setScore(PlayDataUtils.parseDoubleValue(resultItemse.getAll().get("评分")));
						collectedData.setScoreUserCount(PlayDataUtils.parseIntegerValue(resultItemse.getAll().get("评分人数")));

						// 附加信息
						collectedData.addCNName("coinCount", "硬币数");
						collectedData.addCNName("payMoneyCount", "承包数");
						collectedData.addExtendData("coinCount", PlayDataUtils.parseIntegerValue(resultItemse.getAll().get("硬币数")));
						collectedData.addExtendData("payMoneyCount", PlayDataUtils.parseIntegerValue(resultItemse.getAll().get("承包数")));
						collectedDataList.add(collectedData);
					}
				} catch (Exception e) {
					logger.error("生成数据发生错误", e);
				}

			}

			break;
		}


		// 处理aid
		String baseApiUrl = "http://api.bilibili.com/archive_stat/stat?aid=";
		for (int i = 0; i < aidList.size(); i++) {
			try {
				String result = restTemplate.getForObject(baseApiUrl + aidList.get(i), String.class);
				Map<String, Object> map = XJsonUtil.fromJsonString(result);
				map = (Map<String, Object>) map.get("data");
				int aid = (int) map.get("aid");
				Long 播放数 = PlayDataUtils.parseValue(map.get("view"));
				int 弹幕总数 = (int) map.get("danmaku");
				int 评论数 = (int) map.get("reply");
				int 收藏数 = (int) map.get("favorite");
				int 硬币数 = (int) map.get("coin");
				int 分享数 = (int) map.get("share");
				int 喜欢数 = (int) map.get("like");
				int 当前排名 = (int) map.get("now_rank");
				int 历史排名 = (int) map.get("his_rank");

				Long 追番人数 = 追番人数List.get(i);
				Long 承包数 = 承包数List.get(i);

				CollectedDataInflux collectedData = new CollectedDataInflux();

				collectedData.setSite(siteList.get(i));
				collectedData.setType(PlayDataConst.SOURCE_TYPE_EPISODE); // TODO 参数化
				collectedData.setName(nameList.get(i));
				collectedData.addTag("aid", "aid");
				collectedData.addExtendData("aid", String.valueOf(aid));
				collectedData.setPlayCount(播放数);
				collectedData.setDanmuCount(弹幕总数);
				collectedData.setCommentCount(评论数);
				collectedData.setCollectCount(收藏数);
				collectedData.setShareCount(分享数);
				collectedData.setLikeCount(喜欢数);
				collectedData.setRanking(当前排名);
//				collectedData.addField("历史排名", 历史排名) // ???
				collectedData.setChasingCount(追番人数 == null ? null : 追番人数.intValue());
				collectedData.addCNName("coinCount", "硬币数");
				collectedData.addCNName("payMoneyCount", "承包数");
				collectedData.addExtendData("coinCount", 硬币数);
				collectedData.addExtendData("payMoneyCount", 承包数);
				collectedDataList.add(collectedData);
			} catch (Exception e) {
				logger.error("生成数据发生错误, aid:{}", aidList.get(i), e);
			}
		}

		return collectedDataList;
	}

}
