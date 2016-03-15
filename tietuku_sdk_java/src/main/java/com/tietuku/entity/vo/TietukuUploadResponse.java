package com.tietuku.entity.vo;

public class TietukuUploadResponse {

	/** 图片的宽 */
	int width;
	/** 图片的高 */
	int height;
	/** 图片大小(单位:字节) */
	int size;
	/** 图片UBB引用代码 */
	String ubburl;
	/** 图片HTML引用代码 */
	String htmlurl;
	/** 图片原图地址 */
	String linkurl;
	/** 图片展示图地址 */
	String s_url;
	/** 图片缩略图地址 */
	String t_url;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getUbburl() {
		return ubburl;
	}

	public void setUbburl(String ubburl) {
		this.ubburl = ubburl;
	}

	public String getHtmlurl() {
		return htmlurl;
	}

	public void setHtmlurl(String htmlurl) {
		this.htmlurl = htmlurl;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getS_url() {
		return s_url;
	}

	public void setS_url(String s_url) {
		this.s_url = s_url;
	}

	public String getT_url() {
		return t_url;
	}

	public void setT_url(String t_url) {
		this.t_url = t_url;
	}

}
