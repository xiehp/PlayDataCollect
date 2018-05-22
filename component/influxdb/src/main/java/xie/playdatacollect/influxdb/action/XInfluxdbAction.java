package xie.playdatacollect.influxdb.action;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import xie.common.utils.log.XLog;
import xie.common.utils.string.XStringUtils;
import xie.common.utils.utils.XFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XInfluxdbAction {

	private InfluxDB influxDB;
	//	@Resource()
//	private XInfluxdbProperties influxdbProperties;
	private Logger logger = XLog.getLog(this.getClass());

	@Autowired
	public XInfluxdbAction(InfluxDB influxDB) {
		this.influxDB = influxDB;
//		this.influxdbProperties = influxdbProperties;
	}

	public void writePoint(Point point) {
		influxDB.write(point);
	}

	/**
	 * 将SERIES包括index完全移除
	 * DROP SERIES FROM <measurement_name[,measurement_name]> WHERE <tag_key>='<tag_value>'
	 *
	 * @param deleteTagsMap
	 */
//	public List<HashMap<String, Object>> queryData(String database, String measurementName, Map<String, String> deleteTagsMap) {
//		Map<String, Object> map = new LinkedHashMap<>(deleteTagsMap);
//		String where = XFormatUtils.formatMap2InfluxdbLine(map);
//		String query ="DROP SERIES from " + measurementName + " where " + where;
//		logger.info(query);
//		QueryResult queryResult = influxDB.query(new Query(query, database));
//		if (queryResult.hasError()) {
//			logger.error(queryResult.getError());
//			return false;
//		}
//
//		return true;
//	}
//
//	public void writePoints(List<Point> points) {
//		BatchPoints batchPoints = BatchPoints.database(influxDB.da).points(points.toArray(new Point[]{})).build();
//		influxDB.write(batchPoints);
//	}

	/**
	 * 将SERIES包括index完全移除
	 * DROP SERIES FROM <measurement_name[,measurement_name]> WHERE <tag_key>='<tag_value>'
	 *
	 * @param deleteTagsMap
	 */
	public boolean dropSeries(String database, String measurementName, Map<String, String> deleteTagsMap) {
		Map<String, Object> map = new LinkedHashMap<>(deleteTagsMap);
		String where = XFormatUtils.formatMap2InfluxdbLine(map);
		String query = "DROP SERIES from " + measurementName + " where " + where;
		logger.info(query);
		QueryResult queryResult = influxDB.query(new Query(query, database));
		if (queryResult.hasError()) {
			logger.error(queryResult.getError());
			return false;
		}

		return true;
	}

	/**
	 * 删除数据, >= startDate, < endDate
	 *
	 * @param database database
	 * @param measurementName measurementName
	 * @param deleteTagsMap deleteTagsMap
	 * @param startDate 大于等于该时间
	 * @param endDate 小于该时间
	 * @return
	 */
	public boolean deleteSeries(String database, String measurementName, Map<String, String> deleteTagsMap, LocalDateTime startDate, LocalDateTime endDate) {
		Map<String, Object> map = new LinkedHashMap<>(deleteTagsMap);
		String tagWhereStr = XFormatUtils.formatMap2InfluxdbLine(map);

		String query = "DELETE from " + measurementName + " where 1=1 ";
		if (XStringUtils.isNotBlank(tagWhereStr)) {
			query += " and " + tagWhereStr;
		}
		if (startDate != null) {
			query += String.format(" and time >= '%s'", startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
//		if (endDate != null) {
//			query += String.format(" and time < '%s'", endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		}
		logger.info(query);
		QueryResult queryResult = influxDB.query(new Query(query, database));
		if (queryResult.hasError()) {
			logger.error(queryResult.getError());
			return false;
		}

		return true;
	}
}
