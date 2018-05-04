package xie.playdatacollect.core.db.entity.tsd;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测量数据
 */
@Entity
@Table(name = "measurement")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeasurementEntity extends BaseEntity {
	/** 名字 */
	private String name;

}
