package xie.framework.core.service.dictionary.entity;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用于生成序列数据
 */
@Entity
@Table(name = "sys_auto_queue")
public class AutoQueueEntity extends BaseEntity {

	/** 数字 英文 日期 年月 */
	private String type;

	/** 查询用代码 唯一 */
	@Column(unique = true, nullable = false)
	private String code;

	private String nowValue;
	private String preValue;
	private String step;

	@Column(nullable = false)
	private Long nowNumber;
	@Column(nullable = false)
	private Long stepNumber;
	@Column(nullable = true)
	private Long preNumber;

	private String format;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNowValue() {
		return nowValue;
	}

	public void setNowValue(String nowValue) {
		this.nowValue = nowValue;
	}

	public String getPreValue() {
		return preValue;
	}

	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Long getNowNumber() {
		return nowNumber;
	}

	public void setNowNumber(Long nowNumber) {
		this.nowNumber = nowNumber;
	}

	public Long getPreNumber() {
		return preNumber;
	}

	public void setPreNumber(Long preNumber) {
		this.preNumber = preNumber;
	}

	public Long getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(Long stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
