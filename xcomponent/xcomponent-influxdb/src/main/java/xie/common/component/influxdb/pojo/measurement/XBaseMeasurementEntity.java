package xie.common.component.influxdb.pojo.measurement;

import org.influxdb.annotation.Measurement;

/**
 * @author xie
 */
public class XBaseMeasurementEntity {

	public static String getMeasurementName(Class<? extends XBaseMeasurementEntity> clazz) {
		return clazz.getAnnotation(Measurement.class).name();
	}

	public String getMeasurementName() {
		return this.getClass().getAnnotation(Measurement.class).name();
	}
}
