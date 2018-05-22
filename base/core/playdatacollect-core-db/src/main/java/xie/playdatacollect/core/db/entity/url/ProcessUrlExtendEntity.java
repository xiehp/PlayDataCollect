package xie.playdatacollect.core.db.entity.url;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 最终需要处理的有数据的URL
 */
//@Entity
//@Table(name = "process_url_extend")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUrlExtendEntity extends BaseEntity {

	/** 所属数据来源 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sourceKey",referencedColumnName = "keyword", insertable = false, updatable = false)
	SourcesEntity sources;

}
