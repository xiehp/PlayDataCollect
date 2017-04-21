package xie.module.language.translate.baidu;

import org.apache.commons.codec.digest.DigestUtils;

public class XBaiduTranslateUtils {
	public static String getBaiduSign(String appid, String queryText, String salt, String appkey) {
		return DigestUtils.md5Hex(appid + queryText + salt + appkey);
	}
}
