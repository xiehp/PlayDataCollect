package xie.playdatacollect.influxdb.action;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import xie.common.utils.date.DateUtil;
import xie.common.utils.log.XLog;
import xie.common.utils.utils.XFormatUtils;
import xie.playdatacollect.influxdb.data.XInfluxdbPojoMapper;
import xie.playdatacollect.influxdb.pojo.measuerment.XBaseMeasurementEntity;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class XInfluxdbAction {

	//	@Resource()
//	private XInfluxdbProperties influxdbProperties;
	private Logger logger = XLog.getLog(this.getClass());

	private InfluxDB influxDB;
	private XInfluxdbPojoMapper xInfluxdbPojoMapper = new XInfluxdbPojoMapper();

	@Autowired
	public XInfluxdbAction(InfluxDB influxDB) {
		this.influxDB = influxDB;
		influxDB.enableGzip();
//		this.influxdbProperties = influxdbProperties;
	}


	public void writePoint(Point point) {
		influxDB.write(point);
	}

	/**
	 * @param database
	 * @param measurementName
	 * @param queryTagsMap
	 * @param newTagsMap
	 * @param startDate
	 * @param endDate
	 * @return -1 错误， 0 无复制 1 正常
	 */
	public <T extends XBaseMeasurementEntity> int copyData(String database, Class<T> measurementClass, Map<String, String> queryTagsMap, Map<String, String> newTagsMap, Date startDate, Date endDate) throws ParseException {
		// TODO
		QueryResult queryResult = queryDataResult(database, XBaseMeasurementEntity.getMeasurementName(measurementClass), queryTagsMap, startDate, endDate);
		return copyData(queryResult, database, measurementClass, newTagsMap);
	}

	public <T extends XBaseMeasurementEntity> int copyData(QueryResult queryResult, String database, Class<T> measurementClass, Map<String, String> newTagsMap) throws ParseException {
		List<T> measurementEntityList = xInfluxdbPojoMapper.result2Pojo(queryResult, measurementClass);


		List<QueryResult.Result> list = queryResult.getResults();
		if (list.get(0).getSeries() != null && list.get(0).getSeries().size() > 0) {
			QueryResult.Series series = list.get(0).getSeries().get(0);
			List<String> listColumns = series.getColumns();
			List<List<Object>> listValues = series.getValues();
			if (listValues == null || listValues.size() == 0) {
				return 0;
			}

			BatchPoints.Builder batchPointsBuilder = BatchPoints.database(database);
//			for (String tagKey : newTagsMap.keySet()) {
//				batchPointsBuilder.tag(tagKey, newTagsMap.get(tagKey));
//			}

			Map<String, Field> tagsJavaFieldMap = xInfluxdbPojoMapper.getTagsJavaFieldMap(measurementClass);
			Map<String, Field> fieldsJavaFieldMap = xInfluxdbPojoMapper.getFieldsJavaFieldMap(measurementClass);

			// 遍历返回值，如果在XBaseMeasurementEntity中存在，则使用，否则使用原始值
			for (int i = 0; i < listValues.size(); i++) {
				List<Object> values = listValues.get(i); // QueryResult中的值列表
				XBaseMeasurementEntity measurementEntity = measurementEntityList.get(i); // 转换后的pojo

				Point.Builder pointBuilder = Point.measurement(measurementEntity.getMeasurementName());
				Map<String, Object> filedValues = new HashMap<>();
				for (int j = 0; j < listColumns.size(); j++) {
					String columnName = listColumns.get(j);
					if ("time".equals(columnName)) {
						continue;
					}

					Object val = values.get(j);
					if (fieldsJavaFieldMap.containsKey(columnName)) {
						val = xInfluxdbPojoMapper.getFieldValue(measurementEntity, fieldsJavaFieldMap.get(columnName));
					}

					if (tagsJavaFieldMap.containsKey(columnName)) {
						pointBuilder.tag(columnName, (String) val);
					} else {
						filedValues.put(columnName, val);
					}
				}
				pointBuilder.tag(newTagsMap); // 放入新的tag
				pointBuilder.fields(filedValues);
				pointBuilder.time(DateUtil.fromStringUTC((String) values.get(0)).getTime(), TimeUnit.MILLISECONDS);

				batchPointsBuilder.point(pointBuilder.build());
			}

			BatchPoints batchPoints = batchPointsBuilder.build();

			influxDB.write(batchPoints);
		}

		return 1;
	}

	/**
	 * dropMeasurement
	 */
	public List<QueryResult.Result> dropMeasurement(String database, String measurementName) {
		String query = "DROP MEASUREMENT \"" + measurementName + "\"";
		logger.info(query);
		QueryResult queryResult = influxDB.query(new Query(query, database));
		if (queryResult.hasError()) {
			logger.error(queryResult.getError());
			throw new InfluxDBException("query error:" + query + ", " + queryResult.getError());
		}

		return queryResult.getResults();
	}

	/**
	 * dropMeasurement
	 */
	public List<QueryResult.Result> createMeasurement(String database, String measurementName) {
		String query = "CREATE MEASUREMENT \"" + measurementName + "\"";
		logger.info(query);
		QueryResult queryResult = influxDB.query(new Query(query, database));
		if (queryResult.hasError()) {
			logger.error(queryResult.getError());
			throw new InfluxDBException("query error:" + query + ", " + queryResult.getError());
		}

		return queryResult.getResults();
	}

	/**
	 * 请求数据
	 */
	public List<QueryResult.Result> queryDataList(String database, String measurementName, Map<String, String> queryTagsMap, Date startDate, Date endDate) {
		QueryResult queryResult = queryDataResult(database, measurementName, queryTagsMap, startDate, endDate);
		return queryResult.getResults();
	}

	/**
	 * 请求数据
	 */
	public QueryResult queryDataResult(String database, String measurementName, Map<String, String> queryTagsMap, Date startDate, Date endDate) {
		String query = "SELECT * from \"" + measurementName + "\"";

		String str = " where ";
		if (queryTagsMap != null && queryTagsMap.size() > 0) {
			Map<String, Object> map = new LinkedHashMap<>(queryTagsMap);
			String tagWhereStr = XFormatUtils.formatMap2InfluxdbLine(map);
			query += str + tagWhereStr;
			str = " and ";
		}
		if (startDate != null) {
			query += String.format(str + " time >= '%s'", DateUtil.convertToStringUTC(startDate));
			str = " and ";
		}
		if (endDate != null) {
			query += String.format(str + " time < '%s'", DateUtil.convertToStringUTC(endDate));
			str = " and ";
		}

		logger.info(query);
		QueryResult queryResult = influxDB.query(new Query(query, database));
		if (queryResult.hasError()) {
			logger.error(queryResult.getError());
			throw new InfluxDBException("query error:" + query + ", " + queryResult.getError());
		}

		return queryResult;
	}

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
		Assert.hasText(measurementName, "measurementName不能为空");

		String query = "DROP SERIES from \"" + measurementName + "\"";
		if (deleteTagsMap != null && deleteTagsMap.size() > 0) {
			Map<String, Object> map = new LinkedHashMap<>(deleteTagsMap);
			String where = XFormatUtils.formatMap2InfluxdbLine(map);
			query += " where " + where;
		}

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
	 * @param database        database
	 * @param measurementName measurementName
	 * @param deleteTagsMap   deleteTagsMap
	 * @param startDate       大于等于该时间
	 * @param endDate         小于该时间
	 * @return
	 */
	public boolean deleteSeries(String database, String measurementName, Map<String, String> deleteTagsMap, Date startDate, Date endDate) {
		String query = "DELETE from \"" + measurementName + "\" where 1=1 ";
		boolean hasWhere = false;
		if (deleteTagsMap != null && deleteTagsMap.size() > 0) {
			Map<String, Object> map = new LinkedHashMap<>(deleteTagsMap);
			String tagWhereStr = XFormatUtils.formatMap2InfluxdbLine(map);
			query += " and " + tagWhereStr;
			hasWhere = true;
		}
		if (startDate != null) {
			query += String.format(" and time >= '%s'", DateUtil.convertToStringUTC(startDate));
			hasWhere = true;
		}
		if (endDate != null) {
			query += String.format(" and time < '%s'", DateUtil.convertToStringUTC(endDate));
			hasWhere = true;
		}
		if (!hasWhere) {
			query = query.replace("where 1=1", "");
		}
		logger.info(query);
		QueryResult queryResult = influxDB.query(new Query(query, database));
		if (queryResult.hasError()) {
			logger.error(queryResult.getError());
			return false;
		}

		return true;
	}
}
