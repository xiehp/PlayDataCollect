package xie.playdatacollect.influxdb;

import org.influxdb.InfluxDB;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.playdatacollect.influxdb.action.XInfluxdbAction;

@Configuration
@EnableConfigurationProperties(XInfluxdbProperties.class)
public class XInfluxdbConfiguration {

	@Bean
	public XInfluxdbAction getXInfluxdbAction(InfluxDB influxDB, XInfluxdbProperties xInfluxdbProperties) {
		if (xInfluxdbProperties.getDatabase() != null) {
			influxDB.setDatabase(xInfluxdbProperties.getDatabase());
		}
		return new XInfluxdbAction(influxDB);
	}
}
