package xie.common.component.influxdb.action;

import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import xie.common.utils.log.XLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class XInfluxdbActionSimple {

	private Logger logger = XLog.getLog(this.getClass());
	private XInfluxdbAction influxdbAction;
	private String databaseName;
	private String measurementName;

	/**
	 * 请求数据
	 */
	public List<QueryResult.Result> queryData(String database, String measurementName, Map<String, String> queryTagsMap) {
		return influxdbAction.queryDataList(databaseName, measurementName, queryTagsMap, null, null);
	}

	/**
	 * PS: 数据库和measurement由该点决定<br>
	 * <p>
	 * TODO Point没有get函数，这个应该改成自定义数据，方便修改
	 */
	public void writePoint(Point point) {
		influxdbAction.writePoint(point);
	}

	/**
	 * 将SERIES包括index完全移除
	 * DROP SERIES FROM <measurement_name[,measurement_name]> WHERE <tag_key>='<tag_value>'
	 *
	 * @param deleteTagsMap
	 */
	public boolean dropSeries(Map<String, String> deleteTagsMap) {
		return influxdbAction.dropSeries(databaseName, measurementName, deleteTagsMap);
	}

	/**
	 * 删除数据, >= startDate, < endDate
	 *
	 * @param deleteTagsMap deleteTagsMap
	 * @param startDate     大于等于该时间
	 * @param endDate       小于该时间
	 * @return
	 */
	public boolean deleteSeries(Map<String, String> deleteTagsMap, Date startDate, Date endDate) {
		return influxdbAction.deleteSeries(databaseName, measurementName, deleteTagsMap, startDate, endDate);
	}
}
