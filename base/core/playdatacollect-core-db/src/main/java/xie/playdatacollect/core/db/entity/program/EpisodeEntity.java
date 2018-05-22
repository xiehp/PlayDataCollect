package xie.playdatacollect.core.db.entity.program;

import xie.common.spring.jpa.entity.BaseEntity;

import javax.persistence.*;

/**
 * 剧集表
 */
@Entity
@Table(name = "episode")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EpisodeEntity extends BaseEntity {

	/** 节目ID */
	@Column(nullable = false)
	private String programId;

	/** 节目 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "programId", insertable = false, updatable = false)
	private ProgramEntity program;

	/** 识别用代码，唯一，自增长生成，用双数表示 2 4 6 8 ...*/
	@Column(unique = true, nullable = false)
	private Long code;

	/** 显示用完整名称 节目的完整名 + 空格 + 集数名称 */
	@Column(unique = true, nullable = false)
	private String fullName;

	/** 节目的完整名 */
	private String name;

	/** 集数唯一识别号 */
	private String divisionNumber;

	/** 集数名称，唯一， 如第1集，第2集，OVA1，番外，总集篇，PV1 */
	private String divisionName;

	/** 副标题 */
	private String title;

	/** 系列 */
	private String series;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public ProgramEntity getProgram() {
		return program;
	}

	public void setProgram(ProgramEntity program) {
		this.program = program;
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
