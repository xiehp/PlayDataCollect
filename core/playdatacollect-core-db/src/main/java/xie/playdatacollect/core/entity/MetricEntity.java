package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 指标
 */
@Entity
@Table(name = "test1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MetricEntity extends BaseEntity {
	/** 指标名字 */
	private String name;

	/** 指标关键字 */
	private String key;
}
