package xie.common.spring.autoconfigure.influx;

import org.influxdb.InfluxDB;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.common.component.influxdb.action.XInfluxdbAction;

@Configuration
@ConditionalOnClass({InfluxDB.class, InfluxDbAutoConfiguration.class})
@AutoConfigureAfter(InfluxDbAutoConfiguration.class)
@EnableConfigurationProperties(XInfluxdbProperties.class)
public class XInfluxdbAutoConfiguration {


	private final XInfluxdbProperties properties;

	public XInfluxdbAutoConfiguration(XInfluxdbProperties properties) {
		this.properties = properties;
	}

	@Bean
//	@ConditionalOnBean(InfluxDB.class)
	@ConditionalOnMissingBean
	public XInfluxdbAction getXInfluxdbAction(InfluxDB influxDB) {
		if (properties.getDatabase() != null) {
			influxDB.setDatabase(properties.getDatabase());
		}
		return new XInfluxdbAction(influxDB);
	}
}
