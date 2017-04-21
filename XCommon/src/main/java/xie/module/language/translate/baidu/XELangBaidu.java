package xie.module.language.translate.baidu;

import java.util.LinkedHashMap;
import java.util.Map;

import xie.base.XEnum;
import xie.module.language.XELangLocal;

public class XELangBaidu extends XEnum<XELangBaidu> {

	private static final long serialVersionUID = 1282763272188771916L;

	/** 本地语言转换为百度对应的语言的映射表 PS:放前面是因为这个对象需要最先加载，否则会null */
	private static final Map<XELangLocal, XELangBaidu> localMapping = new LinkedHashMap<>();

	public static final XELangBaidu auto = new XELangBaidu("auto", "自动检测", "auto", XELangLocal.UNKNOW);
	public static final XELangBaidu zh = new XELangBaidu("zh", "中文", "简体中文", XELangLocal.ZH_CN);
	public static final XELangBaidu cht = new XELangBaidu("cht", "繁体中文", "繁體中文", XELangLocal.ZH_TW);
	public static final XELangBaidu jp = new XELangBaidu("jp", "日语", "日本語", XELangLocal.JA);
	public static final XELangBaidu en = new XELangBaidu("en", "英语", "English", XELangLocal.EN_US);
	public static final XELangBaidu yue = new XELangBaidu("yue", "粤语", "粤语", XELangLocal.ZH_YUE);
	public static final XELangBaidu wyw = new XELangBaidu("wyw", "文言文", "文言文", XELangLocal.ZH_WYW);
	public static final XELangBaidu kor = new XELangBaidu("kor", "韩语", "한국어", XELangLocal.KO);
	public static final XELangBaidu ru = new XELangBaidu("ru", "俄语", "русский", XELangLocal.RU);
	public static final XELangBaidu ara = new XELangBaidu("ara", "阿拉伯语", "عربي", XELangLocal.AR);
	public static final XELangBaidu fra = new XELangBaidu("fra", "法语", "français", XELangLocal.FR);
	public static final XELangBaidu de = new XELangBaidu("de", "德语", "Deutsch", XELangLocal.DE);
	public static final XELangBaidu th = new XELangBaidu("th", "泰语", "ภาษาไทย", XELangLocal.TH);
	public static final XELangBaidu vie = new XELangBaidu("vie", "越南语", "tiếng Việt", XELangLocal.VI);
	public static final XELangBaidu spa = new XELangBaidu("spa", "西班牙语", "español", XELangLocal.ES);
	public static final XELangBaidu pt = new XELangBaidu("pt", "葡萄牙语", "Português", XELangLocal.PT);
	public static final XELangBaidu it = new XELangBaidu("it", "意大利语", "Italiano", XELangLocal.IT);
	public static final XELangBaidu pl = new XELangBaidu("pl", "波兰语", "polskim", XELangLocal.PL);

	/** 用该语言显示的名字 */
	private String originalName;

	/** 语言转换用 */
	private XELangLocal langLocal;

	/**
	 * @param value 百度翻译中设定的名字
	 * @param name 中文名
	 * @param originalName 用该语言显示的名字
	 */
	public XELangBaidu(String value, String name, String originalName, XELangLocal langLocal) {
		super(value, name);

		this.originalName = originalName;
		this.langLocal = langLocal;

		if (langLocal != null) {
			localMapping.put(langLocal, this);
		}
	}

	public String getOriginalName() {
		return originalName;
	}

	public XELangLocal getLangLocal() {
		return langLocal;
	}

	public static XELangBaidu getLanguage(XELangLocal lan) {
		if (lan == null) {
			return null;
		}

		return localMapping.get(lan);
	}

	public static XELangLocal getLanguageLocal(String baiduLanValue) {
		XELangBaidu xeLangBaidu = parseValue(baiduLanValue);
		if (xeLangBaidu == null) {
			return null;
		} else {
			return xeLangBaidu.getLangLocal();
		}
	}

	public static XELangBaidu parseValue(String value) {
		return parseValue(value, XELangBaidu.class);
	}
}
