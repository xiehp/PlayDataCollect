package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectEntity extends BaseEntity {
	/** 名字 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
