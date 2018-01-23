package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 测量值
 */
@Entity
@Table(name = "test1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ValueEntity extends BaseEntity {
	/** 标签 */
	private String tag;
	/** 时间 */
	private Date time;
	/** 值 */
	private String value;

}
