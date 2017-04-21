package xie.module.language;

import xie.base.XEnum;

public class XELangLocal extends XEnum<XELangLocal> {

	private static final long serialVersionUID = -169248691828096276L;

	/** 未知 */
	public static final XELangLocal UNKNOW = new XELangLocal("UNKNOW", "未知", 99);
	/** 简体中文 */
	public static final XELangLocal ZH_CN = new XELangLocal("ZH_CN", "简体中文", 1);
	/** 繁体中文 */
	public static final XELangLocal ZH_TW = new XELangLocal("ZH_TW", "繁体中文", 2);
	/** 日语 */
	public static final XELangLocal JA = new XELangLocal("JA", "日语", 3);
	/** 英语(美国) */
	public static final XELangLocal EN_US = new XELangLocal("EN_US", "英语(美国)", 4);
	/** 阿拉伯语 */
	public static final XELangLocal AR = new XELangLocal("AR", "阿拉伯语", 10);
	/** 粤语 (百度翻译专用) */
	public static final XELangLocal ZH_YUE = new XELangLocal("ZH_YUE", "粤语", 21);
	/** 文言文 (百度翻译专用) */
	public static final XELangLocal ZH_WYW = new XELangLocal("ZH_WYW", "文言文", 22);
	/** 韩语 */
	public static final XELangLocal KO = new XELangLocal("KO", "韩语", 23);
	/** 俄语 */
	public static final XELangLocal RU = new XELangLocal("RU", "俄语", 24);
	/** 法语 */
	public static final XELangLocal FR = new XELangLocal("FR", "法语", 26);
	/** 德语 */
	public static final XELangLocal DE = new XELangLocal("DE", "德语", 27);
	/** 泰语 */
	public static final XELangLocal TH = new XELangLocal("TH", "泰语", 28);
	/** 越南语 */
	public static final XELangLocal VI = new XELangLocal("VI", "越南语", 29);
	/** 西班牙语 */
	public static final XELangLocal ES = new XELangLocal("ES", "西班牙语", 30);
	/** 葡萄牙语 */
	public static final XELangLocal PT = new XELangLocal("PT", "葡萄牙语", 31);
	/** 意大利语 */
	public static final XELangLocal IT = new XELangLocal("IT", "意大利语", 32);
	/** 波兰语 */
	public static final XELangLocal PL = new XELangLocal("PL", "波兰语", 33);

	private int order;

	public XELangLocal(String value, String name, int order) {
		super(value, name);

		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public static XELangLocal parseValue(String value) {
		return parseValue(value, XELangLocal.class);
	}
}
