package xie.playdatacollect.base.db.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity extends IdEntity implements IBaseEntity {

	private static final long serialVersionUID = -4913519870385296330L;

	public static final String COLUMN_CREATE_BY = "createBy";
	public static final String COLUMN_CREATE_DATE = "createDate";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_DATE = "updateDate";
	public static final String COLUMN_DELETE_FLAG = "deleteFlag";
	public static final String COLUMN_VERSION = "version";
	
	//private String getTableName();

	@CreatedBy
	private String createBy;
	@CreatedDate
	private Date createDate;
	@LastModifiedBy
	private String updateBy;
	@LastModifiedDate
	private Date updateDate;
	@Version
	private Long version = 0L;

	private Integer deleteFlag = 0;

	// public String getId() {
	// return id;
	// }
	//
	// public void setId(String id) {
	// this.id = id;
	// }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * 将内容拷贝到vo中
	 */
	public <X> X copyTo(X vo) {
		BeanUtils.copyProperties(this, vo);
		return vo;
	}

	/**
	 * 将除了baseEntity中的所有内容拷贝到vo中
	 */
	public <X extends BaseEntity> X copyToWithOutBaseInfo(X vo) {
		BaseEntity tempBaseEntity = new BaseEntity();
		BeanUtils.copyProperties(vo, tempBaseEntity);
		BeanUtils.copyProperties(this, vo);
		BeanUtils.copyProperties(tempBaseEntity, vo);
		return vo;
	}

	/**
	 * 从vo中将内容拷贝过来
	 */
	public <X> BaseEntity copyFrom(X vo) {
		BeanUtils.copyProperties(vo, this);
		return this;
	}

	/**
	 * 从vo中将除了baseEntity的所有内容拷贝过来
	 */
	public <X extends BaseEntity> BaseEntity copyFromWithOutBaseInfo(X vo) {
		BaseEntity tempBaseEntity = new BaseEntity();
		BeanUtils.copyProperties(this, tempBaseEntity);
		BeanUtils.copyProperties(vo, this);
		BeanUtils.copyProperties(tempBaseEntity, this);
		return this;
	}
}
