package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

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
public class MetricEntity extends BaseEntity {

	/** 指标关键字 */
	@Column(name = "key1")
	private String key;
	/** 指标名字 */
	private String name;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
