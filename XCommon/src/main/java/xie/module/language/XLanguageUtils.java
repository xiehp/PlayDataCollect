package xie.module.language;

import java.io.UnsupportedEncodingException;

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
