package xie.playdatacollect.influxdb.pojo.measuerment;

import org.influxdb.annotation.Measurement;

import java.util.List;

public class XBaseMeasurementEntity {

	public static String getMeasurementName(Class<? extends XBaseMeasurementEntity> clazz) {
		return clazz.getAnnotation(Measurement.class).name();
	}

	public String getMeasurementName() {
		return this.getClass().getAnnotation(Measurement.class).name();
	}
}
