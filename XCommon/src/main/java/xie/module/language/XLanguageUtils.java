package xie.module.language;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.hankcs.hanlp.HanLP;

/**
 * 使用HanLP类库
 */
public class XLanguageUtils {

	/**
	 * 转换成简体中文
	 */
	public static String convertToSC(String Value) {
		return HanLP.convertToSimplifiedChinese(Value);
	}

	/**
	 * 转换成繁体中文
	 */
	public static String convertToTC(String Value) {
		return HanLP.convertToTraditionalChinese(Value);
	}

	/**
	 * 全文搜索的简繁对应，将搜索文本进行简繁转换，如果转换出不同版本，则追加到后面去，以使当前全文搜索具有搜索简繁的功能。<br>
	 * PS:此方法由于追加多余文本，会导致搜索结果的排序出现偏差。如果有其他更好的办法，则替换。<br>
	 * 
	 * @return 原文本基础上，增加了简繁对应的文本
	 */
	public static String chineseFullTextChange(String sourceText) {
		if (sourceText == null) {
			sourceText = "";
		}

		StringBuilder newSb = new StringBuilder(sourceText);
		String scText = null;
		String tcText = null;
		Set<String> sourceTextSet = new HashSet<>();
		for (String oneText : sourceText.split(" ")) {
			sourceTextSet.add(oneText);
		}

		Set<String> newTextSet = new HashSet<>();
		newTextSet.addAll(sourceTextSet);
		for (String oneText : sourceTextSet) {
			scText = convertToSC(oneText);
			if (!newTextSet.contains(scText)) {
				newSb.append(" " + scText);
				newTextSet.add(scText);
			}

			tcText = convertToTC(oneText);
			if (!newTextSet.contains(tcText)) {
				newSb.append(" " + tcText);
				newTextSet.add(tcText);
			}
		}

		return newSb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String aaa = new String("中文简体繁体日语英语皇后以後以后以太网乙太網");
		String aaa2 = "「以後等妳當上皇后，就能買士多啤梨慶祝了」";
		String aaa3 = "“以后等你当上皇后，就能买草莓庆祝了”";

		System.out.println(HanLP.convertToSimplifiedChinese(aaa));
		System.out.println(HanLP.convertToSimplifiedChinese(aaa2));
		System.out.println(HanLP.convertToSimplifiedChinese(aaa3));

		System.out.println(HanLP.convertToTraditionalChinese(aaa));
		System.out.println(HanLP.convertToTraditionalChinese(aaa2));
		System.out.println(HanLP.convertToTraditionalChinese(aaa3));
	}
}
