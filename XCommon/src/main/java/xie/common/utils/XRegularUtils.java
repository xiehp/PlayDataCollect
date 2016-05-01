package xie.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XRegularUtils {
	public static List<String> find(String str, String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		List<String> findList = new ArrayList<>();
		while (matcher.find()) {
			findList.add(matcher.group());
		}
		
		return findList;
	}
	
	public static void main(String[] args) {
		find("{\\blur4}ここなら 楽しく暮らせそう", "\\{\\\\.*\\}");
		find("{\\blur4}{\\an8}你說什麼？", "\\{\\\\.*\\}");
		find("{\\blur4}{\\an8}你說什麼？", "\\{\\\\.*?\\}");
		find("{\\blur4}{an8}你說什麼？", "\\{\\\\.*\\}");
		find("{\\blur4}{an8}你說什麼？", "\\{\\\\.*?\\}");

		System.out.println("{\\blur4}{\\an8}你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{\\blur4}{an8}你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{\\blur4}{\\an8}你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{\\blur4}你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{\\ blur4}你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{blur4}你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{\\blur4}   {  \\an8}  你說什麼？".replaceAll("\\{\\\\.*?\\}", ""));
		System.out.println("{\\blur4}   {  \\an8}  你說什麼？".replaceAll("\\{.*?\\\\.*?\\}", ""));
		System.out.println("{\\blur4}   {\\an8}  你說什麼？".replaceAll("\\{.*?\\\\.*?\\}", ""));
		System.out.println("{\\blur4}   {\\an8}  {an8}  你說什麼？".replaceAll("\\{.*?\\\\.*?\\}", ""));
	}
}

