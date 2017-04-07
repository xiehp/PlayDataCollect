package xie.module.language.translate.baidu;

import java.util.LinkedHashMap;
import java.util.Map;

import xie.base.XEnum;
import xie.module.language.XELangLocal;

public class XELangBaidu extends XEnum {

	public static final XELangBaidu auto = new XELangBaidu("auto", "自动检测");
	public static final XELangBaidu zh = new XELangBaidu("zh", "中文");
	public static final XELangBaidu cht = new XELangBaidu("cht", "繁体中文");
	public static final XELangBaidu en = new XELangBaidu("en", "英语");
	public static final XELangBaidu yue = new XELangBaidu("yue", "粤语");
	public static final XELangBaidu wyw = new XELangBaidu("wyw", "文言文");
	public static final XELangBaidu jp = new XELangBaidu("jp", "日语");
	public static final XELangBaidu kor = new XELangBaidu("kor", "韩语");
	public static final XELangBaidu fra = new XELangBaidu("fra", "法语");
	public static final XELangBaidu th = new XELangBaidu("th", "泰语");
	public static final XELangBaidu ara = new XELangBaidu("ara", "阿拉伯语");
	public static final XELangBaidu ru = new XELangBaidu("ru", "俄语");
	public static final XELangBaidu vie = new XELangBaidu("vie", "越南语");

	public XELangBaidu() {
	}

	public XELangBaidu(String value, String name) {
		super(value, name);
	}

	private static final Map<XELangLocal, XELangBaidu> localMap = new LinkedHashMap<>();
	static {
		localMap.put(XELangLocal.UNKNOW, auto);
		localMap.put(XELangLocal.ZH_CN, zh);
		localMap.put(XELangLocal.ZH_TW, cht);
		localMap.put(XELangLocal.JA, jp);
		localMap.put(XELangLocal.EN_US, en);
		localMap.put(XELangLocal.AR, ara);
	}

	public static XELangBaidu getLanguage(XELangLocal lan) {
		return localMap.get(lan);
	}
}
