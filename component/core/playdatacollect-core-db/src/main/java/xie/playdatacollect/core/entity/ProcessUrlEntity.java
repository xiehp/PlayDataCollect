package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 有数据的URL
 */
@Entity
@Table(name = "process_url")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUrlEntity extends BaseEntity {

	/** 所属来源 */
	String parentSource;

	String type;
	

	String url;

}
