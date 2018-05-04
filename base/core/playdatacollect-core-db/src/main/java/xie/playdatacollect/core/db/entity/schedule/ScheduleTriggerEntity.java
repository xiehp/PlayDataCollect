package xie.playdatacollect.core.db.entity.schedule;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 最终需要处理的有数据的URL
 */
@Entity
@Table(name = "schedule_trigger")
public class ScheduleTriggerEntity extends BaseEntity {

	public static final String GROUP_DEFAULT = "DEFAULT";

	public static final String TYPE_CRON = "cron";
	public static final String TYPE_SIMPLE = "simple";

	private String identity;

	private String groups;

	private String jobIdentity;

	private String jobGroup;

	/** 定时器客户端代码修改后，如果改动比较大无法兼容，则会新建一个版本 */
	private String versionName;

	/** 记录当前版本的时间，用于程序启动时从代码更新到数据库 */
	private Date versionDate;

	/** 配置时间，更新到定时器的时间 */
	private Date versionSetupDate;

	/** cron, simple */
	private String type;

	private Date startTime;
	private Date endTime;

	private int repeatCount;
	private int repeatInterval;

	private String cron;

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

	public String getJobIdentity() {
		return jobIdentity;
	}

	public void setJobIdentity(String jobIdentity) {
		this.jobIdentity = jobIdentity;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getJsonOptions() {
		return jsonOptions;
	}

	public void setJsonOptions(String jsonOptions) {
		this.jsonOptions = jsonOptions;
	}
}
