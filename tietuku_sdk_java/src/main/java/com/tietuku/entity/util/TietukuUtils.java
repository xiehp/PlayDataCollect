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
	public static String getImageUrlPrefix(String linkUrl) {
		int startIndex = linkUrl.lastIndexOf("/");
		return linkUrl.substring(0, startIndex + 1);
	}

	public static String getImageOriginalUrl(String imageUrlPrefix, String imageUrlId) {
		if (imageUrlPrefix == null || imageUrlId == null) {
			return null;
		}

		return imageUrlPrefix + imageUrlId + TietukuConstants.ImageUrl_Original + ".jpg";
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
		System.out.println(getImageUrlPrefix("http://aaa.xxx.ccc/123456.jpgd"));
	}
}
