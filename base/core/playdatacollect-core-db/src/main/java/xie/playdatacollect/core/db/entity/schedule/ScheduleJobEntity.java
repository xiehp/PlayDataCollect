package xie.playdatacollect.core.db.entity.schedule;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 最终需要处理的有数据的URL
 */
@Entity
@Table(name = "schedule_job")
public class ScheduleJobEntity extends BaseEntity {

	private String identity;

	private String groups;

	/** 定时器客户端代码修改后，如果改动比较大无法兼容，则会新建一个版本 */
	private String versionName;

	/** 记录当前版本的时间，用于程序启动时从代码更新到数据库 */
	private Date versionDate;

	/** 配置时间，更新到定时器的时间 */
	private Date versionSetupDate;

	private String fullClassName;

	private String jsonData;

	private String jsonOptions;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public Date getVersionSetupDate() {
		return versionSetupDate;
	}

	public void setVersionSetupDate(Date versionSetupDate) {
		this.versionSetupDate = versionSetupDate;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getJsonOptions() {
		return jsonOptions;
	}

	public void setJsonOptions(String jsonOptions) {
		this.jsonOptions = jsonOptions;
	}
}
