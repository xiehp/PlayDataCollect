package com.tietuku.entity.util;

import com.tietuku.entity.constant.TietukuConstants;

public class TietukuUtils {
	/**
	 * 根据linkUrl获得贴图库图片的url的ID
	 * 
	 * @param linkUrl
	 * @return
	 */
	public static String getImageUrlID(String linkUrl) {
		int startIndex = linkUrl.lastIndexOf("/");
		int endIndex = linkUrl.lastIndexOf(".");
		return linkUrl.substring(startIndex + 1, endIndex);
	}

	/**
	 * 根据linkUrl获得贴图库图片的url的前缀， http://www.XXX.com部分
	 * 
	 * @param linkUrl
	 * @return
	 */
	public static String getImageUrlPrefix(String linkUrl, boolean safeHttpFlag) {
		int startIndex = linkUrl.lastIndexOf("/");
		String urlPrefix = linkUrl.substring(0, startIndex + 1);
		if (safeHttpFlag) {
			urlPrefix = urlPrefix.replace("http://", "https://");
		} else {
			urlPrefix = urlPrefix.replace("https://", "http://");
		}
		return urlPrefix;
	}

	/**
	 * 根据linkUrl获得贴图库图片的url的前缀， http://www.XXX.com部分，并且去掉http部分
	 * 
	 * @param linkUrl
	 * @return
	 */
	public static String getImageUrlPrefixWithoutHttp(String linkUrl) {
		int startIndex = linkUrl.lastIndexOf("/");
		String urlPrefix = linkUrl.substring(0, startIndex + 1);
		urlPrefix = urlPrefix.replace("http://", "//");
		urlPrefix = urlPrefix.replace("https://", "//");
		return urlPrefix;
	}

	/**
	 * 获得贴图库原始文件URL
	 * 
	 * @param imageUrlPrefix
	 * @param imageUrlId
	 * @param ext 带点的扩展名，默认.jpg
	 * @return
	 */
	public static String getImageOriginalUrl(String imageUrlPrefix, String imageUrlId, String ext) {
		if (imageUrlPrefix == null || imageUrlId == null) {
			return null;
		}

		return imageUrlPrefix + imageUrlId + TietukuConstants.ImageUrl_Original + ext;
	}

	public static String getImageOriginalUrl(String imageUrlPrefix, String imageUrlId) {
		return getImageOriginalUrl(imageUrlPrefix, imageUrlId, ".jpg");
	}

	public static String getImageShowUrl(String imageUrlPrefix, String imageUrlId) {
		if (imageUrlPrefix == null || imageUrlId == null) {
			return null;
		}

		return imageUrlPrefix + imageUrlId + TietukuConstants.ImageUrl_Show + ".jpg";
	}

	public static String getImageThumbnailUrl(String imageUrlPrefix, String imageUrlId) {
		if (imageUrlPrefix == null || imageUrlId == null) {
			return null;
		}

		return imageUrlPrefix + imageUrlId + TietukuConstants.ImageUrl_Thumbnail + ".jpg";
	}

	public static void main(String[] args) {
		System.out.println(getImageUrlID("http://aaa.xxx.ccc/123456.jpgd"));
		System.out.println(getImageUrlPrefix("http://aaa.xxx.ccc/123456.jpgd", true));
		System.out.println(getImageUrlPrefix("http://aaa.xxx.ccc/123456.jpgd", false));
		System.out.println(getImageUrlPrefix("https://aaa.xxx.ccc/123456.jpgd", false));
		System.out.println(getImageUrlPrefixWithoutHttp("https://aaa.xxx.ccc/123456.jpgd"));
	}
}
