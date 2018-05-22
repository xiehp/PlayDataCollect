package xie.playdatacollect.influxdb.action;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration
//@ActiveProfiles(value = "test")
//@EnableAutoConfiguration(exclude = {QuartzAutoConfiguration.class})
@EnableAutoConfiguration
public class XInfluxdbActionTest {

	@Autowired
	private XInfluxdbAction xInfluxdbAction;
	@Autowired
	private InfluxDB influxDB;

	@Test
	public void test_writePoint() {
		influxDB.setDatabase("test");
		influxDB.query(new Query("create database \"test\"", null));
		Point point1 = Point.measurement("m1").tag("t1", "aaa").addField("f1", 111).build();
		xInfluxdbAction.writePoint(point1);
		Point point2 = Point.measurement("m1").tag("t1", "bbb").addField("f1", 222).build();
		Point point3 = Point.measurement("m1").tag("t2", "ccc").addField("f1", 333).build();
		xInfluxdbAction.writePoint(point2);
		xInfluxdbAction.writePoint(point3);
	}


	@Test
	public void test_dropSeries() {
		Map<String, String> tagMap = new HashMap<>();
		tagMap.put("t1", "aaa");
		xInfluxdbAction.dropSeries("test","m1", tagMap);

		tagMap = new HashMap<>();
		tagMap.put("t1", "bbb");
		xInfluxdbAction.dropSeries("test","m1", tagMap);
//		influxDB.query(new Query("DROP SERIES FROM \"m1\" where \"t1\"='aaa'", "test"));


		tagMap.put("t1", "");
		xInfluxdbAction.dropSeries("test","m1", tagMap);
	}

}
