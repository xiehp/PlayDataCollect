package xie.playdatacollect.core.entity.program;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系列表
 */
@Entity
@Table(name = "series")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SeriesEntity extends BaseEntity {
	/** 名字 */
	private String name;

}
