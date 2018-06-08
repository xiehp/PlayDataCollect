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
import xie.common.utils.date.XDateUtil;
import xie.common.utils.log.XLog;
import xie.common.utils.utils.XFormatUtils;
import xie.playdatacollect.influxdb.data.XInfluxdbPojoMapper;
import xie.playdatacollect.influxdb.pojo.XBaseMeasurementEntity;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class XInfluxdbRequest {

	//	@Resource()
//	private XInfluxdbProperties influxdbProperties;
	private Logger logger = XLog.getLog(this.getClass());

	private InfluxDB influxDB;
	private XInfluxdbPojoMapper xInfluxdbPojoMapper = new XInfluxdbPojoMapper();

	@Autowired
	public XInfluxdbRequest(InfluxDB influxDB) {
		this.influxDB = influxDB;
		influxDB.enableGzip();
//		this.influxdbProperties = influxdbProperties;
	}

}
