package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测量标签
 */
@Entity
@Table(name = "test1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TagEntity extends BaseEntity {
	/** 标签名字 */
	private String name;

	/** 标签名字 */
	private String name;

	/** 标签关键字 */
	private String key;
}
