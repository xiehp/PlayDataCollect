package xie.common.component.influxdb.data.measurement;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import xie.common.component.influxdb.pojo.measurement.XBaseMeasurementEntity;

import java.time.Instant;

@Measurement(name="m2")
public class TestM2 extends XBaseMeasurementEntity {

	@Column(name = "time")
	private Instant time;

	@Column(name = "t1", tag = true)
	private String t1;
	@Column(name = "t2", tag = true)
	private String t2;
	@Column(name = "f1")
	private Double f1;
	@Column(name = "f2")
	private String f2;
	@Column(name = "f3")
	private Long f3;

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public Double getF1() {
		return f1;
	}

	public void setF1(Double f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public Long getF3() {
		return f3;
	}

	public void setF3(Long f3) {
		this.f3 = f3;
	}
}
