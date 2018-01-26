package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测量标签
 */
@Entity
@Table(name = "tag")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TagEntity extends BaseEntity {

	/** 标签关键字 */
	private String key;
	/** 标签名字 */
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
