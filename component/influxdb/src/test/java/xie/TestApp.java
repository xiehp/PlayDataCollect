package xie;

import org.influxdb.InfluxDB;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import xie.common.component.influxdb.action.XInfluxdbAction;

@SpringBootConfiguration
public class TestApp {
	@Bean
	public XInfluxdbAction getXInfluxdbAction(InfluxDB influxDB) {
		return new XInfluxdbAction(influxDB);
	}

}
