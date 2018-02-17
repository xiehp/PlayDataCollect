package xie.playdatacollect.core.entity.program;

import xie.playdatacollect.base.db.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "episode")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EpisodeEntity extends BaseEntity {
	/** 名字 */
	private String name;

	private String url;

	private String site;
}
