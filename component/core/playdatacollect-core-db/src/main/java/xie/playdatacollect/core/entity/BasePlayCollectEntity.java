package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePlayCollectEntity extends BaseEntity {

	/** 用于逻辑表示唯一的字段 */
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}