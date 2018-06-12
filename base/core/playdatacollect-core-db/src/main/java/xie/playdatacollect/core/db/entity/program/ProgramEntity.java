package xie.playdatacollect.core.db.entity.program;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 节目表 用于记录某个具体节目的数据
 * <p>
 * <p>
 * 当前表关系：
 * <p>
 * 系列表：{@link SeriesEntity} <br>
 * 表示某个具体的节目所属的系列
 * <p>
 * 节目表：{@link ProgramEntity} <br>
 * 表示某个具体的节目数据
 * <p>
 * 剧集表：{@link EpisodeEntity} <br>
 * 表示某个节目下分集数据
 */
@Entity
@Table(name = "program")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProgramEntity extends BaseEntity {

	/** 识别用代码，唯一，自增长生成，用单数表示 1 3 5 7 ... */
	@Column(unique = true, nullable = false)
	private Long code;

	/** 显示用完整名称 名称 + 空格 + 季度号 */
	@Column(unique = true, nullable = false)
	private String fullName;

	/** 名称 */
	private String name;

	/** 季度号唯一识别号 */
	private String divisionNumber;

	/** 季度号，唯一， 如第一季，第二季，番外篇，外传 */
	private String divisionName;

	/** 副标题 */
	private String title;

	/** 系列 */
	private String series;

	/** 来源列表，用逗号隔开 */
	private String sourceKeys;

//	/** 版本编号 */
//	private String versionNumber;
//
//	/** 版本名字 TV版，OVA，先行版，web版*/
//	private String versionName;
//
//	/**
//	 * 分类
//	 *
//	 * 电视剧 电影 动画 动画电影
//	 */
//	private String type1;
//
//	/**
//	 * 真人 动画 木偶 橡皮
//	 */
//	private String type2;
//
//	/**
//	 * 2D, 3D
//	 */
//	private String type3;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivisionNumber() {
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

}
