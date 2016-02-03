package com.tietuku.entity.token;

import com.tietuku.entity.util.Base64Util;
import com.tietuku.entity.util.HmacUtil;
import com.tietuku.entity.util.PathConfig;


public class Token {
	/**
	 * 创建TOKEN
	 * @param deadlineTime
	 * @param albumId
	 * @return
	 */
	public static String createToken(long deadlineTime ,long albumId){
		String accesskey = PathConfig.getProperty("tie.tu.ku.accessKey");
		String secretKey = PathConfig.getProperty("tie.tu.ku.secretKey");		
		String json = "{\"deadline\":%s ,\"album\":\"%s\",\"returnBody\":\"\"}";
		json = String.format(json, deadlineTime , albumId);
		String base64param = Base64Util.base64(json.getBytes()).trim();		
		String sign = HmacUtil.hmac_sha1(base64param , secretKey);
		String token = accesskey+":"+sign+":"+base64param;
		return token;
	}
	
	/**
	 * 创建TOKEN
	 * @param deadlineTime
	 * @param albumId
	 * @return
	 */
	public static String createToken(long deadlineTime){
		String accesskey = PathConfig.getProperty("tie.tu.ku.accessKey");
		String secretKey = PathConfig.getProperty("tie.tu.ku.secretKey");		
		String json = "{\"deadline\":%s}";
		json = String.format(json, deadlineTime);
		String base64param = Base64Util.base64(json.getBytes()).trim();		
		String sign = HmacUtil.hmac_sha1(base64param , secretKey);
		String token = accesskey+":"+sign+":"+base64param;
		return token;
	}
	
	/**
	 * 创建TOKEN
	 * @param deadlineTime
	 * @param albumId
	 * @param returnJson
	 * @return
	 */
	public static String createToken(long deadlineTime ,long albumId , String returnJson){
		String accesskey = PathConfig.getProperty("tie.tu.ku.accessKey");
		String secretKey = PathConfig.getProperty("tie.tu.ku.secretKey");		
		String json = "{\"deadline\":%s ,\"album\":\"%s\",\"returnBody\":%s}";
		json = String.format(json, deadlineTime , albumId , returnJson);
		String base64param = Base64Util.base64(json.getBytes()).trim();		
		String sign = HmacUtil.hmac_sha1(base64param , secretKey);
		String token = accesskey+":"+sign+":"+base64param;
		return token;
	}
	
	/**
	 * 创建TOKEN
	 * @param deadlineTime
	 * @param albumId
	 * @param returnJson
	 * @return
	 */
	public static String createToken(String accesskey , String secretKey, long deadlineTime ,long albumId , String returnJson){
		String json = "{\"deadline\":%s ,\"album\":\"%s\",\"returnBody\":\"%s\"}";
		json = String.format(json, deadlineTime , albumId , returnJson);
		String base64param = Base64Util.base64(json.getBytes()).trim();		
		String sign = HmacUtil.hmac_sha1(base64param , secretKey);
		String token = accesskey+":"+sign+":"+base64param;
		return token;
	}
}
