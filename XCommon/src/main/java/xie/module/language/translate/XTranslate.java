package xie.module.language.translate;

import xie.module.language.XELangLocal;

public interface XTranslate {

	/**
	 * 翻译成某个语言，原始语言自动检测
	 * 
	 * @param lang 翻译语言
	 */
	String translate(XELangLocal lang);

	/**
	 * 指定原始语言，翻译成某个语言
	 *
	 * @param langFrom 原始语言
	 * @param langTo 翻译语言
	 */
	String translate(XELangLocal langFrom, XELangLocal langTo);
}
