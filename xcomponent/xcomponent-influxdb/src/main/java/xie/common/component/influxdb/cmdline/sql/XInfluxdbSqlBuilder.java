package xie.common.component.influxdb.cmdline.sql;

import xie.common.component.influxdb.action.XInfluxdbActionParameter;
import xie.common.utils.date.XDateUtil;
import xie.common.utils.string.XStringUtils;
import xie.common.utils.utils.XFormatUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class XInfluxdbSqlBuilder {

	public String buildSelectSql(XInfluxdbActionParameter parameter) {
		StringBuilder querySql = new StringBuilder("SELECT * ");
		if (XStringUtils.isNotBlank(parameter.getSelectSql())) {
			querySql = new StringBuilder(parameter.getSelectSql());
		} else {
			if (parameter.getGroupByTagNameList() != null && parameter.getGroupByTagNameList().size() > 0) {
				querySql = new StringBuilder("SELECT MAX(*) ");
			}
		}

		// FROM SQL
		querySql.append(" FROM \"" + parameter.getMeasurement() + "\"");

		// tags filter
		Map<String, String> queryTagsMap = parameter.getTags();
		String whereOrAndStr = " WHERE ";
		if (queryTagsMap != null && queryTagsMap.size() > 0) {
			Map<String, String> map = new LinkedHashMap<>(queryTagsMap);
			String tagWhereStr = XFormatUtils.formatMap2InfluxdbLine(map);
			querySql.append(whereOrAndStr).append(tagWhereStr);
			whereOrAndStr = " AND ";
		}

		// values filter
		Map<String, Object> queryFieldsMap = parameter.getFields();
		if (queryFieldsMap != null && queryFieldsMap.size() > 0) {
			Map<String, Object> map = new LinkedHashMap<>(queryFieldsMap);
			String fieldWhereStr = XFormatUtils.formatMap2InfluxdbLine(map);
			querySql.append(whereOrAndStr).append(fieldWhereStr);
			whereOrAndStr = " AND ";
		}

		// time filter
		Date startDate = parameter.getStartDate();
		Date endDate = parameter.getEndDate();
		if (startDate != null) {
			querySql.append(String.format(whereOrAndStr + " time >= '%s' ", XDateUtil.convertToStringUTC(startDate)));
			whereOrAndStr = " AND ";
		}
		if (endDate != null) {
			querySql.append(String.format(whereOrAndStr + " time < '%s' ", XDateUtil.convertToStringUTC(endDate)));
			whereOrAndStr = " AND ";
		}

		String groupByStr = " GROUP BY ";
		// group
		if (XStringUtils.isNotBlank(parameter.getGroupByTime())) {
			querySql.append(groupByStr).append("time(").append(parameter.getGroupByTime()).append(")");
			groupByStr = ", ";
		}

		// group
		if (parameter.getGroupByTagNameList() != null && parameter.getGroupByTagNameList().size() > 0) {
			if ("*".equals(parameter.getGroupByTagNameList().get(0))) {
				querySql.append(groupByStr).append("*");
				groupByStr = ", ";
			} else {
				for (String groupTagName : parameter.getGroupByTagNameList()) {
					querySql.append(groupByStr).append("\"").append(groupTagName).append("\"");
					groupByStr = ", ";
				}
			}
		}

		// order

		// Fill
		if (XStringUtils.isNotBlank(parameter.getFill())) {
			querySql.append(" FILL(" + parameter.getFill() + ")");
		}

		return querySql.toString();
	}
}
