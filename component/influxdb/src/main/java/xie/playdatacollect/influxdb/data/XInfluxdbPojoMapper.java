package xie.playdatacollect.influxdb.data;

import org.influxdb.annotation.Column;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import xie.common.utils.java.XReflectionUtils;
import xie.playdatacollect.influxdb.pojo.XBaseMeasurementEntity;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * influxdb的pojo转换类
 */
public class XInfluxdbPojoMapper {
	InfluxDBResultMapper influxDBResultMapper = new InfluxDBResultMapper();

	private static final ConcurrentMap<String, ConcurrentMap<String, Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();

	private static final ConcurrentMap<String, ConcurrentMap<String, Field>> CLASS_TATS_CACHE = new ConcurrentHashMap<>();

	/**
	 * 将influxdb的http查询结果转换为pojo
	 */
	public <T extends XBaseMeasurementEntity> List<T> result2Pojo(QueryResult queryResult, Class<T> measurementEntityClass) {
		return influxDBResultMapper.toPOJO(queryResult, measurementEntityClass);
	}

	/**
	 * 将pojo转换为点
	 */
	public <T extends XBaseMeasurementEntity> List<Point> pojoList2PointList(List<T> measurementEntityList) {
		List<Point> list = new ArrayList<>();
		for (XBaseMeasurementEntity measurementEntity : measurementEntityList) {
			list.add(pojo2Point(measurementEntity));
		}
		return list;
	}

	/**
	 * 将pojo转换为点
	 */
	public Point pojo2Point(XBaseMeasurementEntity measurementEntity) {
		Objects.requireNonNull(measurementEntity, "measurementEntity");

		cacheMeasurementClass(measurementEntity.getClass());
		ConcurrentMap<String, Field> javaFiledMap = CLASS_FIELD_CACHE.get(measurementEntity.getClass().getName());

		Point.Builder pointBuilder = Point.measurement(measurementEntity.getMeasurementName());
		Map<String, Object> influxdbFieldsMap = new HashMap<>();
		for (String key : javaFiledMap.keySet()) {
			Field field = javaFiledMap.get(key);
			Column colAnnotation = field.getAnnotation(Column.class);

			// tag
			Object val = getFieldValue(measurementEntity, field);
			if (colAnnotation.tag()) {
				if (val == null) {
					pointBuilder.tag(colAnnotation.name(), "");
				} else {
					pointBuilder.tag(colAnnotation.name(), (String) val);
				}
			} else {
				influxdbFieldsMap.put(colAnnotation.name(), val);
			}
		}
		// field
		pointBuilder.fields(influxdbFieldsMap);

		Point point = pointBuilder.build();
		return point;
	}

	private void cacheMeasurementClass(final Class<?>... classVarAgrs) {
		for (Class<?> clazz : classVarAgrs) {
			if (CLASS_FIELD_CACHE.containsKey(clazz.getName())) {
				continue;
			}
			ConcurrentMap<String, Field> initialMap = new ConcurrentHashMap<>();
			ConcurrentMap<String, Field> influxColumnAndFieldMap = CLASS_FIELD_CACHE.putIfAbsent(clazz.getName(), initialMap);
			if (influxColumnAndFieldMap == null) {
				influxColumnAndFieldMap = initialMap;
			}

			ConcurrentMap<String, Field> tagsMap = new ConcurrentHashMap<>();
			for (Field field : clazz.getDeclaredFields()) {
				Column colAnnotation = field.getAnnotation(Column.class);
				if (colAnnotation != null) {
					influxColumnAndFieldMap.put(colAnnotation.name(), field);
					if (colAnnotation.tag()) {
						tagsMap.put(colAnnotation.name(), field);
					}
				}
			}
			CLASS_TATS_CACHE.put(clazz.getName(), tagsMap);
		}
	}

	public Object getFieldValue(Object obj, Field field) {
		return XReflectionUtils.getField(field, obj);
	}

	public <T extends XBaseMeasurementEntity> Map<String, Field> getFieldsJavaFieldMap(Class<? extends XBaseMeasurementEntity> measurementEntityClass) {
		cacheMeasurementClass();
		ConcurrentMap<String, Field> map = CLASS_FIELD_CACHE.get(measurementEntityClass.getName());
		Map<String, Field> newMap = new HashMap<>(map);
		return newMap;
	}

	public Map<String, Field> getTagsJavaFieldMap(Class<? extends XBaseMeasurementEntity> measurementEntityClass) {
		cacheMeasurementClass(measurementEntityClass);
		ConcurrentMap<String, Field> map = CLASS_TATS_CACHE.get(measurementEntityClass.getName());
		Map<String, Field> newMap = new HashMap<>(map);
		return newMap;
	}

}
