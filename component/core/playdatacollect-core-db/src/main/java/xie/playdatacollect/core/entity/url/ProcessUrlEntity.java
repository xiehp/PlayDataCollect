package xie.playdatacollect.core.entity.url;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 最终需要处理的有数据的URL
 */
@Entity
@Table(name = "process_url")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUrlEntity extends BaseEntity {

	/** 所属数据来源 */
	private String sourceKey;

	/** 所属数据来源 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sourceKey",referencedColumnName = "keyword", insertable = false, updatable = false)
	SourcesEntity sources;

	/** url名称 */
	private String name;

	private Date beginDate;

	/** program, episode */
	private String type;

	private String url;

	/** url备注 */
	private String remark;

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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
