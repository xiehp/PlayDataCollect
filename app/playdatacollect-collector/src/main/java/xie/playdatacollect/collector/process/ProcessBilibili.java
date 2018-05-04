package xie.playdatacollect.collector.process;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import xie.common.spring.utils.XJsonUtil;
import xie.common.utils.log.XLog;
import xie.common.utils.string.XStringUtils;
import xie.playdatacollect.common.utils.PlayDataUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ProcessBilibili {

	@Resource
	RestTemplate restTemplate;
	Logger logger = XLog.getLog(this);

	public void spiderGetAll(Spider spider, List<String> list, long collectTime) {

		List<ResultItems> resultItemses = spider.getAll(list);

		InfluxDB influxDB = InfluxDBFactory.connect("https://influxdb.acgimage.cn/");
		influxDB.enableGzip();
		influxDB.setDatabase("play_data");

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
						siteList.add("bilibili");
						nameList.add(名字);
						aidList.add(aid.toString());
						追番人数List.add(PlayDataUtils.parseValue(resultItemse.getAll().get("追番人数")));
						承包数List.add(PlayDataUtils.parseValue(resultItemse.getAll().get("承包数")));
					} else {
						Point.Builder builder = Point.measurement("base_data");
						builder.tag("网站", "bilibili");
						builder.tag("名字", 名字);
//						builder.addField("追番人数", 追番人数);
//						builder.addField("弹幕总数", 弹幕总数);

						builder.addField("播放数", PlayDataUtils.parseValue(resultItemse.getAll().get("播放数")));
						builder.addField("分享数", PlayDataUtils.parseValue(resultItemse.getAll().get("分享数")));
						builder.addField("收藏数", PlayDataUtils.parseValue(resultItemse.getAll().get("收藏数")));
						builder.addField("硬币数", PlayDataUtils.parseValue(resultItemse.getAll().get("硬币数")));
						builder.addField("承包数", PlayDataUtils.parseValue(resultItemse.getAll().get("承包数")));
						builder.addField("评论数", PlayDataUtils.parseValue(resultItemse.getAll().get("评论数")));
						// TODO 评分删除不了
						builder.addField("评价分", PlayDataUtils.parseDoubleValue(resultItemse.getAll().get("评分")));
						builder.addField("评分人数", PlayDataUtils.parseValue(resultItemse.getAll().get("评分人数")));
						builder.time(collectTime, TimeUnit.MILLISECONDS);
						Point point = builder.build();
						influxDB.write(point);
					}
				} catch (Exception e) {
					logger.error("保存influxDB发生错误", e);
				}

//				resultItemse.getAll().forEach((key, value) -> {
//					ValueEntity valueEntity = new ValueEntity();
//					valueEntity.setTag(key);
//					valueEntity.setTime(new Date());
//					valueEntity.setValue(value == null ? null : value.toString());
//					valueEntity = valueService.save(valueEntity);
//					//System.out.println(valueEntity.toMapWithOutBase());
//
//
//					if ("播放数".equals(key) || "追番人数".equals(key) || "弹幕总数".equals(key)) {
//						value = parseValue(value);
//						//String requestObject = "cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000";
//						String requestObject = key + ",名字=" + 名字 + ",网站=bilibili value=" + value + " " + dateTime + "";
//						//String result = restTemplate.postForObject("http://linux2.acgimage.cn:48086/write?db=play_data", requestObject, String.class);
//						//log.info(result);
//					}
//				});

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
				int 播放数 = (int) map.get("view");
				int 弹幕总数 = (int) map.get("danmaku");
				int 评论数 = (int) map.get("reply");
				int 收藏数 = (int) map.get("favorite");
				int 硬币数 = (int) map.get("coin");
				int 分享数 = (int) map.get("share");
				int 喜欢数 = (int) map.get("like");
				int 当前排名 = (int) map.get("now_rank");
				int 历史排名 = (int) map.get("his_rank");

				long 追番人数 = 追番人数List.get(i);
				long 承包数 = 承包数List.get(i);


				Point point = Point.measurement("base_data")
						.tag("网站", siteList.get(i))
						.tag("名字", nameList.get(i))
						.tag("aid", String.valueOf(aid))
						.addField("播放数", 播放数)
						.addField("弹幕总数", 弹幕总数)
						.addField("评论数", 评论数)
						.addField("收藏数", 收藏数)
						.addField("硬币数", 硬币数)
						.addField("分享数", 分享数)
						.addField("喜欢数", 喜欢数)
						.addField("当前排名", 当前排名)
						.addField("历史排名", 历史排名)
						.addField("追番人数", 追番人数)
						.addField("承包数", 承包数)
						.time(collectTime, TimeUnit.MILLISECONDS)
						.build();
				influxDB.write(point);
			} catch (Exception e) {
				logger.error("保存influxDB发生错误, aid:{}", aidList.get(i), e);
			}
		}

		influxDB.close();
		spider.close();
	}
}
