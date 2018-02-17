package xie.playdatacollect.core.entity.program;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 节目表 用于记录某个具体节目的数据
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

	/** 名称 */
	private String name;


}
