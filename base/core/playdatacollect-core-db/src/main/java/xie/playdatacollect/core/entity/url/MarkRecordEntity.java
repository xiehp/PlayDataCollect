package xie.playdatacollect.core.entity.url;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用于标记记录
 */
@Entity
@Table(name = "process_url")
public class MarkRecordEntity extends BaseEntity {
	String type;
	String mark;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
