package xie.module.language.translate.baidu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import xie.module.language.XELangLocal;
import xie.module.language.translate.XTranslate;

@Component(value = "XBaiduTranslate")
public class XBaiduTranslate implements XTranslate {

	/** APP ID */
	@Value("${xie.baidu.translate.appid:}")
	private String appId;

	/** 密钥 */
	@Value("${xie.baidu.translate.key:}")
	private String key;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String translate(XELangLocal lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String translate(XELangLocal langFrom, XELangLocal langTo) {
		// TODO Auto-generated method stub
		return null;
	}

}
