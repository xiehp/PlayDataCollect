package xie.playdatacollect.core.db.entity.url;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import xie.common.spring.jpa.entity.BaseEntity;
import xie.playdatacollect.core.db.entity.program.EpisodeEntity;
import xie.playdatacollect.core.db.entity.program.ProgramEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 最终需要处理的有数据的URL
 */
@Entity
@Table(name = "process_url")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUrlEntity extends BaseEntity {

	private Long programCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "programCode", referencedColumnName = "code", insertable = false, updatable = false)
	private ProgramEntity program;

	private Long episodeCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "episodeCode", referencedColumnName = "code", insertable = false, updatable = false)
	private EpisodeEntity episode;

	/** 所属数据来源 */
	private String sourceKey;

	/** 所属数据来源 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sourceKey", referencedColumnName = "keyword", insertable = false, updatable = false)
	private SourcesEntity sources;

	/** url名称 */
	private String name;

	/** 该URL发布时间 */
	private Date beginDate;

	/** 该URL再发布时间 */
	private Date reBeginDate;

	/** program, episode */
	private String type;

	private String url;

	/** 类型，html，json等 */
	private String urlType;

	/** json格式的字符串 */
	private String params;

	/** 备注 */
	private String remark;

	public Long getProgramCode() {
		return programCode;
	}

	public void setProgramCode(Long programCode) {
		this.programCode = programCode;
	}

	public ProgramEntity getProgram() {
		return program;
	}

	public void setProgram(ProgramEntity program) {
		this.program = program;
	}

	public Long getEpisodeCode() {
		return episodeCode;
	}

	public void setEpisodeCode(Long episodeCode) {
		this.episodeCode = episodeCode;
	}

	public EpisodeEntity getEpisode() {
		return episode;
	}

	public void setEpisode(EpisodeEntity episode) {
		this.episode = episode;
	}

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

	public Date getReBeginDate() {
		return reBeginDate;
	}

	public void setReBeginDate(Date reBeginDate) {
		this.reBeginDate = reBeginDate;
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

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
