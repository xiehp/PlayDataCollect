package xie.playdatacollect.influxdb.data;

import org.influxdb.dto.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import xie.playdatacollect.influxdb.data.measurement.TestM2;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration
//@ActiveProfiles(value = "test")
//@EnableAutoConfiguration(exclude = {QuartzAutoConfiguration.class})
@EnableAutoConfiguration
public class XInfluxdbPojoMapperTest {

	@Test
	public void testPojo2Point() {
		XInfluxdbPojoMapper mapper = new XInfluxdbPojoMapper();
		TestM2 testM2 = new TestM2();
		testM2.setTime(LocalDateTime.of(2018,5,24,11,22).toInstant(ZoneOffset.UTC));
		testM2.setT1("t1");
		testM2.setT2("t2");
		testM2.setF1(1.00);
		testM2.setF2("2.2");
		testM2.setF3(3L);
		Point point = mapper.pojo2Point(testM2);
		System.out.println(point.lineProtocol());
		Assert.assertEquals("m2,t1=t1,t2=t2 f1=1.0,f2=\"2.2\",f3=3i,time=2018-05-24T11:22:00Z", point.lineProtocol());
	}
}
