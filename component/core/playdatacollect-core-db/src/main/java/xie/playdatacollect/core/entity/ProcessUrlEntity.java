package xie.playdatacollect.core.entity;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.*;

/**
 * 最终需要处理的有数据的URL
 */
@Entity
@Table(name = "process_url")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUrlEntity extends BaseEntity {

	/** 所属数据来源 */
	String sourceKey;

	/** 所属数据来源 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sourceKey",referencedColumnName = "keyword", insertable = false, updatable = false)
	SourcesEntity sources;

	/** url名称 */
	String name;

	/** url备注 */
	String remark;

	String type;

	String url;

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public SourcesEntity getSources() {
		return sources;
	}

	public void setSources(SourcesEntity sources) {
		this.sources = sources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
