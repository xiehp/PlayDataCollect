package xie.function.mkvtool;

import xie.common.utils.string.XStringUtils;

public class MkvTrackInfo {

	/** 轨道编号 */
	private Integer trackNo;

	/** 用于 mkvmerge & mkvextract 的轨道 ID */
	private Integer mkvId;

	/** 轨道类型 */
	private String type;

	/** 语言 */
	private String lang;

	/** 名称 */
	private String langName;

	/** 编码格式私有数据，长度 */
	private Long size;

	/** 编码格式 ID */
	private String fileType;

	public Integer getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(Integer trackNo) {
		this.trackNo = trackNo;
	}

	public Integer getMkvId() {
		return mkvId;
	}

	public void setMkvId(Integer mkvId) {
		this.mkvId = mkvId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return XStringUtils.toString2(this);
	}
}
