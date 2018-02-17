package xie.playdatacollect.core.entity.tsd;

import xie.playdatacollect.core.entity.BasePlayCollectEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测量标签
 */
@Entity
@Table(name = "tag")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TagEntity extends BasePlayCollectEntity {

	/** 标签名字 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
