package xie.playdatacollect.collector.workflow.use;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xie.common.utils.log.XLog;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.influxdb.data.CollectedDataInflux;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UseDataProcess {

	protected Logger logger = XLog.getLog(this);

	@Resource
	protected RestTemplate restTemplate;

	/**
	 * 处理整理好的数据
	 *
	 * @param CollectedDataList 已经整理过的数据
	 * @param collectTime 时间
	 */
	public void use(List<CollectedData> CollectedDataList, long collectTime) {

		InfluxDB influxDB = InfluxDBFactory.connect("https://influxdb.acgimage.cn/");
		influxDB.setDatabase("play_data");


		for (CollectedData collectedData : CollectedDataList) {
			try {
				logger.info(collectedData.toString());
				CollectedDataInflux collectedDataInflux = CollectedDataInflux.create(collectedData);
				collectedDataInflux.setTime(collectTime);
				Point point = collectedDataInflux.createPoint();
				influxDB.write(point);
			} catch (Exception e) {
				logger.error("保存influxDB发生错误", e);
			}
		}

		influxDB.close();
	}
}
