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
public class UseDataProcess implements IUseDataProcess {

	protected Logger logger = XLog.getLog(this);

	@Resource
	protected RestTemplate restTemplate;

	@Resource
	protected InfluxDB influxDB;

	/**
	 * 处理整理好的数据
	 *
	 * @param collectedDataList 已经整理过的数据
	 * @param collectTime       时间
	 */
	public void use(List<CollectedData> collectedDataList, long collectTime) {
		if (collectedDataList == null || collectedDataList.size() == 0) {
			return;
		}

		influxDB.setDatabase("play_data");


		for (CollectedData collectedData : collectedDataList) {
			try {
				logger.info(collectedData.toString());
				CollectedDataInflux collectedDataInflux;
				if (collectedData instanceof CollectedDataInflux) {
					collectedDataInflux = (CollectedDataInflux) collectedData;
				} else {
					collectedDataInflux = CollectedDataInflux.create(collectedData);
				}
				collectedDataInflux.setTime(collectTime);
				Point point = collectedDataInflux.createPoint();
				influxDB.write(point);
				logger.info("Point add: {}", point);
			} catch (Exception e) {
				logger.error(collectedData.toString());
				logger.error("保存influxDB发生错误", e);
			}
		}

		influxDB.close();
	}
}
