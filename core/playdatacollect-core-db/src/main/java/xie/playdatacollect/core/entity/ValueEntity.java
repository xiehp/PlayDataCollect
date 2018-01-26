package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 测量值
 */
@Entity
@Table(name = "value")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ValueEntity extends BaseEntity {
	/** 标签 */
	private String tag;
	/** 时间 */
	private Date time;
	/** 值 */
	private String value;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
