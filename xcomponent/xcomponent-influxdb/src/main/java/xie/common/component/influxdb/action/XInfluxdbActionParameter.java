package xie.common.component.influxdb.action;

import java.time.Instant;
import java.util.*;

public class XInfluxdbActionParameter {

	public static final String FILL_NULL = "null";
	public static final String FILL_PREVIOUS = "previous";
	public static final String FILL_NUMBER = "number";
	public static final String FILL_NONE = "none";
	public static final String FILL_LINEAR = "linear";

	private String database;
	private String measurement;
	private Map<String, String> tags;
	private Map<String, Object> fields;
	// 优先Instant
	private Instant startInstant;
	private Instant endInstant;
	private Date startDate;
	private Date endDate;

	private String selectSql; // directly set select sql
	private Map<String, String> groupBySelectMap; // tagName, group by method(MAX,MIN,XX)
	private List<String> groupByTagNameList;
	private String groupByTime;
	private List<String> orderByNameList; // TODO ?? 好像不支持排序
	private boolean orderByTimeDescFlag = false;
	/** 限制返回的数据条数 */
	private int limit;
	/** 限制返回的系列条数 */
	private int sLimit;
	private String fill;
	private String timeZone = "Asia/Shanghai";


	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public void addTag(String tagName, String value) {
		if (tags == null) {
			tags = new LinkedHashMap<>();
			tags.put(tagName, value);
		}
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public void addField(String fieldName, Object value) {
		if (fields == null) {
			fields = new LinkedHashMap<>();
			fields.put(fieldName, value);
		}
	}

	public Instant getStartInstant() {
		return startInstant;
	}

	public void setStartInstant(Instant startInstant) {
		this.startInstant = startInstant;
	}

	public Instant getEndInstant() {
		return endInstant;
	}

	public void setEndInstant(Instant endInstant) {
		this.endInstant = endInstant;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	@Deprecated
	public Map<String, String> getGroupBySelectMap() {
		return groupBySelectMap;
	}

	@Deprecated
	public void setGroupBySelectMap(Map<String, String> groupBySelectMap) {
		this.groupBySelectMap = groupBySelectMap;
	}

	public List<String> getGroupByTagNameList() {
		return groupByTagNameList;
	}

	public void setGroupByTagNameList(List<String> groupByTagNameList) {
		this.groupByTagNameList = groupByTagNameList;
	}

	public void addGroupByTagName(String groupByTagName) {
		if (groupByTagNameList == null) {
			groupByTagNameList = new ArrayList<>();
		}
		groupByTagNameList.add(groupByTagName);
	}

	public String getGroupByTime() {
		return groupByTime;
	}

	public void setGroupByTime(String groupByTime) {
		this.groupByTime = groupByTime;
	}

	public List<String> getOrderByNameList() {
		return orderByNameList;
	}

	public void setOrderByNameList(List<String> orderByNameList) {
		this.orderByNameList = orderByNameList;
	}

	public boolean isOrderByTimeDescFlag() {
		return orderByTimeDescFlag;
	}

	public void setOrderByTimeDescFlag(boolean orderByTimeDescFlag) {
		this.orderByTimeDescFlag = orderByTimeDescFlag;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getSLimit() {
		return sLimit;
	}

	public void setSLimit(int sLimit) {
		this.sLimit = sLimit;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
