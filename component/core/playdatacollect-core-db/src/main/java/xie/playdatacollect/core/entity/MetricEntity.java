package xie.playdatacollect.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 指标
 */
//@Data //可以省略get+set方法。
@Entity
@Table(name = "metric")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MetricEntity extends BasePlayCollectEntity {

	/** 指标名字 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
