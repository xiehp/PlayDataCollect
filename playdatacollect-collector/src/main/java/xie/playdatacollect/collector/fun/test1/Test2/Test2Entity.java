package xie.playdatacollect.collector.fun.test1.Test2;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test2")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Test2Entity extends BaseEntity {
	private String col1;
	private String col2;


	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}
}
