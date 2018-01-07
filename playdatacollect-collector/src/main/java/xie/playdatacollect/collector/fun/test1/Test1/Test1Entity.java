package xie.playdatacollect.collector.fun.test1.Test1;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test1")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Test1Entity extends BaseEntity {
	private String col1;


	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}
}
