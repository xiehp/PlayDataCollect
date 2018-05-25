package xie.playdatacollect.influxdb.action;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import xie.common.utils.date.DateUtil;
import xie.playdatacollect.influxdb.data.XInfluxdbPojoMapper;
import xie.playdatacollect.influxdb.data.measurement.TestM2;
import xie.playdatacollect.influxdb.pojo.XBaseMeasurementEntity;
import xie.playdatacollect.influxdb.pojo.measuerment.MPlayData;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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

	private XInfluxdbPojoMapper xInfluxdbPojoMapper = new XInfluxdbPojoMapper();

	private void prepareData_full() throws ParseException {
		influxDB.setDatabase("test");
		xInfluxdbAction.dropMeasurement("test", "m2");

		// 写入数据
		Point point11 = Point.measurement("m2").tag("t1", "tag red").tag("t2", "tag dog").addField("f1", 111.0).addField("f2", "a").addField("f3", 1).time(DateUtil.fromString("2018-03-02 11:22:33").getTime(), TimeUnit.MILLISECONDS).build();
		Point point12 = Point.measurement("m2").tag("t1", "tag red").tag("t2", "tag dog").addField("f1", 112.0).addField("f2", "b").addField("f3", 2).time(DateUtil.fromString("2018-03-02 11:22:34").getTime(), TimeUnit.MILLISECONDS).build();
		Point point13 = Point.measurement("m2").tag("t1", "tag red").tag("t2", "tag dog").addField("f1", 113.0).addField("f2", "c").addField("f3", 3).time(DateUtil.fromString("2018-03-02 11:22:35").getTime(), TimeUnit.MILLISECONDS).build();
		Point point2 = Point.measurement("m2").tag("t1", "tag red").tag("t2", "tag cat").addField("f1", 222.0).addField("f2", "d").addField("f3", 4).time(DateUtil.fromString("2018-03-02 11:22:33").getTime(), TimeUnit.MILLISECONDS).build();
		Point point3 = Point.measurement("m2").tag("t1", "tag blue").tag("t2", "tag cat").addField("f1", 333.0).addField("f2", "e").addField("f3", 5).time(DateUtil.fromString("2018-03-02 11:22:33").getTime(), TimeUnit.MILLISECONDS).build();
		Point point4 = Point.measurement("m1").tag("t1", "tag blue").tag("t2", "tag cat").addField("f1", 333.0).addField("f2", "e").addField("f3", 5).addField("f4", 5).time(DateUtil.fromString("2018-03-02 11:22:33").getTime(), TimeUnit.MILLISECONDS).build();
		xInfluxdbAction.writePoint(point11);
		xInfluxdbAction.writePoint(point12);
		xInfluxdbAction.writePoint(point13);
		xInfluxdbAction.writePoint(point2);
		xInfluxdbAction.writePoint(point3);
		xInfluxdbAction.writePoint(point4);

		// 读取数据
		List<QueryResult.Result> list;
		QueryResult queryResult = xInfluxdbAction.queryDataResult("test", "m1\",\"m2", null, null, null);
		System.out.println(queryResult.getResults().get(0).getSeries().get(0).getValues());
		Assert.assertEquals(5, queryResult.getResults().get(0).getSeries().get(0).getValues().size());
	}

	@Test
	public void do_copyAndDelete() throws ParseException {
		List<QueryResult.Result> list;
		Map<String, String> tags = new HashMap<>();
		Map<String, String> newTags = new HashMap<>();
		QueryResult queryResult;

		tags.clear();
		tags.put("名字", "IDOLiSH 7 -偶像星愿-");
		tags.put("类型", "");
		newTags.clear();
		newTags.put("类型", "program");

		xInfluxdbAction.copyDataAndDrop("play_data", MPlayData.class, tags, newTags);
	}

	@Test
	public void test_copyAndDelete() throws ParseException {
		List<QueryResult.Result> list;
		Map<String, String> tags = new HashMap<>();
		Map<String, String> newTags = new HashMap<>();
		QueryResult queryResult;
		int size;

		// prepare data
		tags.clear();
		tags.put("网站", "bilibili");
		tags.put("名字", "Four of a Kind 四牌士");

		// drop test data
		xInfluxdbAction.dropSeries("test", "base_data", tags);

		// copy test data

		queryResult = xInfluxdbAction.queryDataResult("play_data", "base_data", tags, null, DateUtil.fromString("2018-05-25 08:00:00"));
		assertSize(queryResult, 11626);
		xInfluxdbAction.copyData(queryResult, "test", MPlayData.class, tags);

		// copy test data
		tags.clear();
		tags.put("网站", "bilibili");
		tags.put("名字", "Four of a Kind");

		queryResult = xInfluxdbAction.queryDataResult("play_data", "base_data", tags, null, DateUtil.fromString("2018-05-25 08:00:00"));
		assertSize(queryResult, 844);
		xInfluxdbAction.copyData(queryResult, "test", MPlayData.class, tags);

		// test
		tags.clear();
		tags.put("网站", "bilibili");
		tags.put("名字", "Four of a Kind");
		tags.put("类型", "");
		newTags.clear();
		newTags.put("网站", "bilibili");
		newTags.put("名字", "Four of a Kind 四牌士");
		newTags.put("类型", "program");

		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", newTags);
		assertSize(queryResult, 5923 - 844);

		xInfluxdbAction.copyDataAndDrop("test", MPlayData.class, tags, newTags);
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags);
		assertSize(queryResult, null);

		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", newTags);
		assertSize(queryResult, 5923);

		tags.clear();
		tags.put("网站", "bilibili");
		tags.put("名字", "Four of a Kind 四牌士");
		tags.put("类型", "");
		newTags.clear();
		newTags.put("网站", "bilibili");
		newTags.put("名字", "Four of a Kind 四牌士");
		newTags.put("类型", "program");

		xInfluxdbAction.copyDataAndDrop("test", MPlayData.class, tags, newTags);

		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags);
		assertSize(queryResult, null);
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", newTags);
		assertSize(queryResult, 12470);
	}

	@Test
	public void test_play_data2() throws ParseException {
		List<QueryResult.Result> list;
		Map<String, String> tags = new HashMap<>();
		QueryResult queryResult;

		tags.clear();
		tags.put("网站", "bilibili");
		tags.put("名字", "齐木楠雄的灾难 第二季");

		queryResult = xInfluxdbAction.queryDataResult("play_data", "base_data", tags);
		print(queryResult, MPlayData.class);
		System.out.println(queryResult.getResults().get(0).getSeries().get(0).getValues().size());

	}

	@Test
	public void test_play_data() throws ParseException {
		List<QueryResult.Result> list;
		Map<String, String> tags = new HashMap<>();
		QueryResult queryResult;
//
//		xInfluxdbAction.dropMeasurement("test", "base_data");

		tags.clear();
		tags.put("网站", "bilibili");

		xInfluxdbAction.deleteSeries("test", "base_data", tags);
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
		System.out.println(queryResult.getResults().get(0).getSeries() == null);


//		tags.put("名字", "重神机潘多拉：第9话 虎眼");
		queryResult = xInfluxdbAction.queryDataResult("play_data", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
//		print(queryResult, MPlayData.class);

		xInfluxdbAction.copyData(queryResult, "test", MPlayData.class, tags);
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
		print(queryResult, MPlayData.class);
		System.out.println(queryResult.getResults().get(0).getSeries().get(0).getValues().size());


		tags.put("网站", "bilibili");
		tags.put("名字", "齐木楠雄的灾难 第二季");
		xInfluxdbAction.deleteSeries("test", "base_data", tags);
		tags.remove("名字");
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
		System.out.println(queryResult.getResults().get(0).getSeries().get(0).getValues().size());

		tags.put("名字", "Butlers～千年百年物语～");
		xInfluxdbAction.deleteSeries("test", "base_data", tags);
		tags.remove("名字");
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
		System.out.println(queryResult.getResults().get(0).getSeries().get(0).getValues().size());

		tags.put("名字", "DARLING in the FRANXX（僅限港澳台地區）");
		xInfluxdbAction.deleteSeries("test", "base_data", tags);
		tags.remove("名字");
		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
		System.out.println(queryResult.getResults().get(0).getSeries().get(0).getValues().size());
//		queryResult = xInfluxdbAction.queryDataResult("test", "base_data", tags, DateUtil.fromString("2018-05-24 23:30:00"), DateUtil.fromString("2018-05-24 23:40:00"));
//		print(queryResult, MPlayData.class);


	}

	@Test
	public void test_full() throws ParseException {
		List<QueryResult.Result> list;
		Map<String, String> tags = new HashMap<>();
		QueryResult queryResult;

		xInfluxdbAction.dropMeasurement("test", "m2");
//		xInfluxdbAction.createMeasurement("test", "m2");

		// delete数据
//		xInfluxdbAction.deleteSeries("test", "m2", null, null, null);
//		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
//		System.out.println(list);

		// drop数据
//		xInfluxdbAction.dropSeries("test", "m2", null);
//		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
//		System.out.println(list);

		// 删除数据0
		prepareData_full();
		tags.clear();
		xInfluxdbAction.deleteSeries("test", "m2", tags, null, null);
		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
		System.out.println(list);
		Assert.assertNull(list.get(0).getSeries());

		// 删除数据1
		prepareData_full();
		tags.clear();
		tags.put("t1", "tag red");
		xInfluxdbAction.deleteSeries("test", "m2", tags, null, null);
		queryResult = xInfluxdbAction.queryDataResult("test", "m2", null, null, null);
		System.out.println(list);
		Assert.assertEquals(1, queryResult.getResults().get(0).getSeries().get(0).getValues().size());
		InfluxDBResultMapper influxDBResultMapper = new InfluxDBResultMapper();
		List<TestM2> listResultM2 = influxDBResultMapper.toPOJO(queryResult, TestM2.class);
		System.out.println(listResultM2);
		print(xInfluxdbPojoMapper.pojoList2PointList(listResultM2));


		// 删除数据2
		prepareData_full();
		tags.clear();
		tags.put("t2", "tag cat");
		xInfluxdbAction.deleteSeries("test", "m2", tags, null, null);
		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
		System.out.println(list);
		Assert.assertEquals(3, list.get(0).getSeries().get(0).getValues().size());

		// 删除数据3
		prepareData_full();
		tags.clear();
		xInfluxdbAction.deleteSeries("test", "m2", tags, DateUtil.fromString("2018-03-02 11:22:34"), null);
		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
		System.out.println(list);
		Assert.assertEquals(3, list.get(0).getSeries().get(0).getValues().size());

		// 删除数据4
		prepareData_full();
		tags.clear();
		xInfluxdbAction.deleteSeries("test", "m2", tags, null, DateUtil.fromString("2018-03-02 11:22:34"));
		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
		System.out.println(list);
		Assert.assertEquals(2, list.get(0).getSeries().get(0).getValues().size());

		// 删除数据5
		prepareData_full();
		tags.clear();
		xInfluxdbAction.deleteSeries("test", "m2", tags, DateUtil.fromString("2018-03-02 11:22:34"), DateUtil.fromString("2018-03-02 11:22:35"));
		list = xInfluxdbAction.queryDataList("test", "m2", null, null, null);
		System.out.println(list);
		Assert.assertEquals(4, list.get(0).getSeries().get(0).getValues().size());


		Map<String, String> newTags = new HashMap<>();
		// 复制数据1
		prepareData_full();
		tags.clear();
		tags.put("t1", "tag red");
		newTags.clear();
		newTags.put("t1", "tag green");
		newTags.put("t2", "tag mouse");
		xInfluxdbAction.copyData("test", TestM2.class, tags, newTags, null, null);
		queryResult = xInfluxdbAction.queryDataResult("test", "m2", null, null, null);
		System.out.println(list);
		print(queryResult);
		Assert.assertEquals(8, queryResult.getResults().get(0).getSeries().get(0).getValues().size());


		// 复制数据2
		prepareData_full();
		tags.clear();
		newTags.clear();
		newTags.put("t1", "tag green");
		newTags.put("t2", "tag mouse");
		xInfluxdbAction.copyData("test", TestM2.class, tags, newTags, DateUtil.fromString("2018-03-02 11:22:34"), DateUtil.fromString("2018-03-02 11:22:35"));
		queryResult = xInfluxdbAction.queryDataResult("test", "m2", null, null, null);
		System.out.println(list);
		print(queryResult);
		Assert.assertEquals(4, list.get(0).getSeries().get(0).getValues().size());


		// 复制数据3
		prepareData_full();
		tags.clear();
		tags.put("t2", "tag cat");
		newTags.clear();
		newTags.put("t2", "tag mouse");
		xInfluxdbAction.copyData("test", TestM2.class, tags, newTags, null, null);
		queryResult = xInfluxdbAction.queryDataResult("test", "m2", null, null, null);
		System.out.println(list);
		print(queryResult);
		Assert.assertEquals(7, queryResult.getResults().get(0).getSeries().get(0).getValues().size());

	}


	private void print(QueryResult queryResult) {
		List<TestM2> listResultM2 = xInfluxdbPojoMapper.result2Pojo(queryResult, TestM2.class);
		System.out.println(listResultM2);
		print(xInfluxdbPojoMapper.pojoList2PointList(listResultM2));
	}

	private <T extends XBaseMeasurementEntity> void print(QueryResult queryResult, Class<T> clazz) {
		List<T> listResultM2 = xInfluxdbPojoMapper.result2Pojo(queryResult, clazz);
//		System.out.println(listResultM2);
		print(xInfluxdbPojoMapper.pojoList2PointList(listResultM2));
	}

	private void print(List<Point> list) {
		for (Point point : list) {
			System.out.println(point);
		}
	}

	private void assertSize(QueryResult queryResult, Integer size) {
		List<QueryResult.Series> listSeries = queryResult.getResults().get(0).getSeries();
		Integer actualSize = null;
		if (listSeries != null) {
			actualSize = listSeries.get(0).getValues().size();
		}
		System.out.println("实际size: " + actualSize);
		Assert.assertEquals(size, actualSize);
	}

	@Test
	public void test_writePoint() throws InterruptedException {
		influxDB.setDatabase("test");
		influxDB.query(new Query("create database \"test\"", null));
		Point point1 = Point.measurement("m1").tag("t1", "aaa").addField("f1", 111).build();

		Point point2 = Point.measurement("m1").tag("t1", "bbb").addField("f1", 222).build();
		Point point3 = Point.measurement("m1").tag("t2", "ccc").addField("f1", 333).build();
//		xInfluxdbAction.writePoint(point1);
//		xInfluxdbAction.writePoint(point2);
		xInfluxdbAction.writePoint(point3);

//		while (true) {
		Thread.sleep(2000);
//			xInfluxdbAction.writePoint(point1);
//			xInfluxdbAction.writePoint(point2);
		xInfluxdbAction.writePoint(point3);
		Thread.sleep(2000);
//		xInfluxdbAction.writePoint(point1);
//		xInfluxdbAction.writePoint(point2);
		xInfluxdbAction.writePoint(point3);

//		}
	}


	@Test
	public void test_dropSeries() {
		Map<String, String> tagMap = new HashMap<>();
		tagMap.put("t1", "aaa");
		xInfluxdbAction.dropSeries("test", "m1", tagMap);

		tagMap = new HashMap<>();
		tagMap.put("t2", "ccc");
		xInfluxdbAction.dropSeries("test", "m1", tagMap);
//		influxDB.query(new Query("DROP SERIES FROM \"m1\" where \"t1\"='aaa'", "test"));


		tagMap.put("t1", "");
		xInfluxdbAction.dropSeries("test", "m1", tagMap);
	}


	@Test
	public void test_deleteSeries() throws ParseException {
		Map<String, String> tagMap = new HashMap<>();
		tagMap.put("t2", "ccc");
		xInfluxdbAction.deleteSeries("test", "m1", tagMap, DateUtil.fromString("2018-05-22 23:38:34"), DateUtil.fromString("2018-05-22 23:38:38"));

	}

}
