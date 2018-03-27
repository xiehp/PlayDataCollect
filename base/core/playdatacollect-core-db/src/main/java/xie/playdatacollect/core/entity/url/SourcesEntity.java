package xie.playdatacollect.core.entity.url;

import xie.playdatacollect.core.entity.BasePlayCollectEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据来源表，表示来自哪个网站
 */
@Entity
@Table(name = "sources")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SourcesEntity extends BasePlayCollectEntity {

	/** 全称 */
	private String name;

	/** 简称 */
	private String simpleName;

	/** 英文缩写 */
	private String abName;

	/** 类型 */
	private String type;

	/** 网址 */
	private String url;

	/** 自定义的版本 */
	private String versionDef;

	/** 自定义的版本时间 */
	private Date versionDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
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

	public String getAbName() {
		return abName;
	}

	public void setAbName(String abName) {
		this.abName = abName;
	}

	public String getVersionDef() {
		return versionDef;
	}

	public void setVersionDef(String versionDef) {
		this.versionDef = versionDef;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}


}
