package xie.common.component.influxdb.action;

import org.influxdb.InfluxDB;
import org.slf4j.Logger;
import xie.common.utils.log.XLog;
import xie.common.component.influxdb.data.XInfluxdbPojoMapper;

public class XInfluxdbRequest {

	//	@Resource()
//	private XInfluxdbProperties influxdbProperties;
	private Logger logger = XLog.getLog(this.getClass());

	private InfluxDB influxDB;
	private XInfluxdbPojoMapper xInfluxdbPojoMapper = new XInfluxdbPojoMapper();

	public XInfluxdbRequest(InfluxDB influxDB) {
		this.influxDB = influxDB;
		influxDB.enableGzip();
//		this.influxdbProperties = influxdbProperties;
	}

}
