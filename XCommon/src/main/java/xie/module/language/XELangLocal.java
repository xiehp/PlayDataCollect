package xie.module.language;

import xie.base.XEnum;

public class XELangLocal extends XEnum {

	public static final XELangLocal UNKNOW = new XELangLocal("UNKNOW", "未知");
	public static final XELangLocal ZH_CN = new XELangLocal("ZH_CN", "简体中文");
	public static final XELangLocal ZH_TW = new XELangLocal("ZH_TW", "繁体中文");
	public static final XELangLocal JA = new XELangLocal("JA", "日语");
	public static final XELangLocal EN_US = new XELangLocal("EN_US", "英语(美国)");
	public static final XELangLocal AR = new XELangLocal("AR", "阿拉伯语");

	public XELangLocal() {
	}

	public XELangLocal(String value, String name) {
		super(value, name);
	}
}
