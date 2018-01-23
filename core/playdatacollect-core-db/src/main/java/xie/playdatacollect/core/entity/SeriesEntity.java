package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系列表
 */
@Entity
@Table(name = "test1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SeriesEntity extends BaseEntity {
	/** 名字 */
	private String name;

}
