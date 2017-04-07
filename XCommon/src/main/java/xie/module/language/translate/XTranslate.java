package xie.module.language.translate;

import xie.module.language.XELangLocal;

public interface XTranslate {

	/**
	 * 翻译成某个语言，原始语言自动检测
	 * 
	 * @param lang
	 * @return
	 */
	String translate(XELangLocal lang);

	/**
	 * 指定院士语言，翻译成某个语言
	 * 
	 * @param lang
	 * @param lang
	 * @return
	 */
	String translate(XELangLocal langFrom, XELangLocal langTo);
}
