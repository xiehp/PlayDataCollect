package xie.subtitle.type;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xie.common.date.XTimeUtils;
import xie.common.string.XStringUtils;
import xie.common.utils.XRegularUtils;
import xie.subtitle.line.LineASS;

public class SubtitleASS extends SubtitleBase {

	public static final String TIME_FORMAT = "H:mm:ss.SSS";
	public static final String REG_TEXT_CODE = "\\{.*?\\\\.*?\\}";

	Map<String, String> paramMap = new HashMap<>();

	/** 字幕行列表 */
	List<String> textLineList = new ArrayList<>();

	/** 字幕行的ASS格式化方式 */
	String[] textLineFormat;

	Map<String, Pattern> filterRemoveEqualMap;
	Map<String, Pattern> filterRemoveLikeMap;
	Map<String, Pattern> filterIncludeEqualMap;
	Map<String, Pattern> filterIncludeLikeMap;

	@Override
	protected void initData() {
		boolean sriptInfoFlg = false; // 是否进入Script Info 部分
		// boolean AegisubProjectGarbage = false; // 是否进入Aegisub Project Garbage部分
		boolean V4StylesFlg = false; // 是否进入V4+ Styles部分

		boolean eventsFlg = false; // 是否进入Events部分
		
		// 1:Script Info 部分 2:Aegisub Project Garbage部分 3:V4+ Styles部分 4:Events部分
		int sectionMark = 0;

		List<String> lineList = getLineList();
		if (lineList == null) {
			return;
		}

		for (int i = 0; i < lineList.size(); i++) {
			String line = lineList.get(i);
			if (line == null) {
				continue;
			}
			line = line.trim();

			if (isSectionFlg(line)) {
				sriptInfoFlg = false;
				V4StylesFlg = false;
				eventsFlg = false;
				if (line.contains("Script Info")) {
					sriptInfoFlg = true;
					sectionMark = 1;
				} else if (line.contains("V4+ Styles")) {
					V4StylesFlg = true;
					sectionMark = 3;
				} else if (line.contains("Events")) {
					eventsFlg = true;
					sectionMark = 4;
				}
			}

			String[] keyValue = getTrimedKeyValue(line);
			if (keyValue != null) {
				String key = keyValue[0];
				String value = keyValue[1];
				paramMap.put(key, value);

				if (sectionMark == 4) {
					if ("Dialogue".equals(key) && isDialogue(value)) {
						textLineList.add(value);
					} else if (key.equals("Format")) {
						textLineFormat = value.split(",");
						for (int j = 0; j < textLineFormat.length; j++) {
							if (textLineFormat[j] != null) {
								textLineFormat[j] = textLineFormat[j].trim();
							} else {
								textLineFormat[j] = "";
							}
						}
					}
				} else if (sriptInfoFlg) {
					// TODO ASS sriptInfoFlg
				} else if (V4StylesFlg) {
					// TODO ASS V4StylesFlg
				}
			}
		}

		if (textLineFormat != null) {
			// 包含排除文字, =完全相等 #包含
			filterRemoveEqualMap = convertFilterStr(getFilterRemove(), "=", true);
			filterRemoveLikeMap = convertFilterStr(getFilterRemove(), "#", false);
			filterIncludeEqualMap = convertFilterStr(getFilterInclude(), "=", true);
			filterIncludeLikeMap = convertFilterStr(getFilterInclude(), "#", false);

			int lineIndex = -1;
			for (String textLine : textLineList) {
				lineIndex++;
				try {
					LineASS lineASS = new LineASS(textLine);
					lineASS.setLineIndex(lineIndex);
					boolean hasStartFlg = false; // 字幕行是否包含开始时间
					boolean hasEndFlg = false; // 字幕行是否包含结束时间
					boolean hasTextFlg = false; // 字幕行是否包含文本
					boolean isRemoveFlg = false; // 是否被过滤

					// 将字幕行分解成最大format的数量
					String[] textLineValues = textLine.split(",", textLineFormat.length);
					int lenth = textLineValues.length;
					String textLineName = "";
					String textLineValue = "";
					// 遍历所有字段，获取对应的值
					for (int i = 0; i < textLineFormat.length; i++) {
						if (i >= lenth) {
							break;
						}

						textLineName = textLineFormat[i];
						textLineValue = textLineValues[i].trim();

						// 检查是否需要排除掉
						if (checkNeedRemove(textLineName, textLineValue)) {
							isRemoveFlg = true;
							break;
						}

						if (textLineName.equalsIgnoreCase("Layer")) {
							lineASS.setLayer(textLineValue);
						} else if (textLineName.equalsIgnoreCase("Start")) {
							lineASS.setStartTime(XTimeUtils.parseFromTimeStr(textLineValue + 0, TIME_FORMAT));
							hasStartFlg = true;
						} else if (textLineName.equalsIgnoreCase("End")) {
							lineASS.setEndTime(XTimeUtils.parseFromTimeStr(textLineValue + 0, TIME_FORMAT));
							hasEndFlg = true;
						} else if (textLineName.equalsIgnoreCase("Style")) {
							lineASS.setStyle(textLineValue);
						} else if (textLineName.equalsIgnoreCase("Name")) {
							lineASS.setName(textLineValue);
						} else if (textLineName.equalsIgnoreCase("MarginL")) {

						} else if (textLineName.equalsIgnoreCase("MarginR")) {

						} else if (textLineName.equalsIgnoreCase("MarginV")) {

						} else if (textLineName.equalsIgnoreCase("Effect")) {

						} else if (textLineName.equalsIgnoreCase("Text")) {
							if (XStringUtils.isNotBlank(textLineValue)) {
								// 除去ASS的特效样式
								String text = textLineValue.replaceAll(REG_TEXT_CODE, "").trim();
								text = text.replace("\\n", " ");
								text = text.replace("\\N", " ");
								text = text.replace("\\h", " ");
								text = text.trim();
								if (XStringUtils.isNotBlank(text)) {
									lineASS.setText(text);
									lineASS.setTextEffect(XRegularUtils.find(textLineValue, REG_TEXT_CODE));
									hasTextFlg = true;
								}
							}
						}
					}

					if (isRemoveFlg) {
						logger.info("被过滤字幕行：name:" + textLineName + ", value:" + textLineValue + ", full:" + textLine);
						continue;
					}

					if (!hasStartFlg || !hasEndFlg) {
						logger.info("缺少开始或结束字幕行：" + textLine);
						continue;
					}

					if (!hasTextFlg) {
						logger.info("缺少文本字幕行：" + textLine);
						continue;
					}

					addSubtitleLine(lineASS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析过滤字符串 eq:name1=value1,name2=value2 like:name1#value1,name2#value2
	 * 
	 * @param filtersStr
	 * @param relation =或者#,区分name和value关系的字符
	 * @param addFullFlag 是否进行完全匹配
	 * @return
	 */
	private Map<String, Pattern> convertFilterStr(String filtersStr, String relation, boolean addFullFlag) {
		// Map<String, String> filterMap = new HashMap<>();
		// if (XStringUtils.isNotBlank(filtersStr)) {
		// String[] filtersArray = filtersStr.split(",");
		// for (String filterStr : filtersArray) {
		// if (filterStr.contains(relation)) {
		// String[] filter = filterStr.split(relation, 2);
		// if (filter.length > 1) {
		// filterMap.put(filter[0].toLowerCase(), filter[1]);
		// } else if (filter.length > 0) {
		// filterMap.put(filter[0].toLowerCase(), "");
		// }
		// }
		// }
		// }

		Map<String, Pattern> filterMap = new HashMap<>();
		if (XStringUtils.isNotBlank(filtersStr)) {
			String[] filtersArray = filtersStr.split(",");
			for (String filterStr : filtersArray) {
				if (filterStr.contains(relation)) {
					String[] filter = filterStr.split(relation, 2);
					if (filter.length > 1) {
						String compareStr = filter[1];
						if (addFullFlag) {
							compareStr = "^" + compareStr + "$";
						}
						Pattern pattern = Pattern.compile(compareStr, Pattern.CASE_INSENSITIVE);
						filterMap.put(filter[0].toLowerCase(), pattern);
						logger.info("增加过滤条件：{}, {}", relation, pattern);
					}
				}
			}
		}

		// String str = "service@xsoftlab.net";
		// // 邮箱验证规则
		// String regEx = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		// // 编译正则表达式
		// Pattern pattern = Pattern.compile(regEx);
		// // 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = pattern.matcher(str);
		// // 字符串是否与正则表达式相匹配
		// boolean rs = matcher.matches();
		// System.out.println(rs);

		return filterMap;
	}

	/**
	 * 判断是否需要排除
	 * 
	 * @param name
	 * @param value
	 * @return true 需要排除
	 */
	private boolean checkNeedRemove(String name, String value) {
		String lowerCaseName = name.toLowerCase();
		// 判断字幕行需要排除
		{
			// String filterValue = filterRemoveEqualMap.get(lowerCalseName);
			// if (XStringUtils.isNotEmpty(filterValue)) {
			// if (value.equalsIgnoreCase(filterValue)) {
			// // 排除字段一致
			// // logger.info("一致排除字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
			// return true;
			// }
			// }
			Pattern pattern = filterRemoveEqualMap.get(lowerCaseName);
			if (pattern != null) {
				Matcher matcher = pattern.matcher(value);
				if (matcher != null && matcher.find()) {
					// 排除字段一致
					// logger.info("一致排除字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
					return true;
				}
			}
		}

		{
			// String filterValue = filterRemoveLikeMap.get(lowerCaseName);
			// if (XStringUtils.isNotEmpty(filterValue)) {
			// if (value.toLowerCase().contains(filterValue.toLowerCase())) {
			// // 排除字段包含
			// // logger.info("包含排除字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
			// return true;
			// }
			// }
			Pattern pattern = filterRemoveLikeMap.get(lowerCaseName);
			if (pattern != null) {
				Matcher matcher = pattern.matcher(value);
				if (matcher != null && matcher.find()) {
					// 排除字段包含
					// logger.info("包含排除字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
					return true;
				}
			}
		}

		// 判断字幕行需要包含
		{
			// String filterValue = filterIncludeEqualMap.get(lowerCaseName);
			// if (XStringUtils.isNotEmpty(filterValue)) {
			// if (!value.equalsIgnoreCase(filterValue)) {
			// // 没有一致字段
			// // logger.info("没有一致字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
			// return true;
			// }
			// }
			Pattern pattern = filterIncludeEqualMap.get(lowerCaseName);
			if (pattern != null) {
				Matcher matcher = pattern.matcher(value);
				if (matcher != null && !matcher.find()) {
					// 排除字段一致
					// logger.info("一致排除字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
					return true;
				}
			}
		}
		{
			// String filterValue = filterIncludeLikeMap.get(lowerCaseName);
			// if (XStringUtils.isNotEmpty(filterValue)) {
			// if (!value.toLowerCase().contains(filterValue.toLowerCase())) {
			// // 没有包含字段
			// // logger.info("没有包含字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
			// return true;
			// }
			// }
			Pattern pattern = filterIncludeLikeMap.get(lowerCaseName);
			if (pattern != null) {
				Matcher matcher = pattern.matcher(value);
				if (matcher != null && !matcher.find()) {
					// 排除字段一致
					// logger.info("一致排除字段：name:" + name + ", value:" + value + ", filterValue:" + filterValue);
					return true;
				}
			}
		}

		return false;
	}

	public static void main(String[] args) {
		System.out.println(XRegularUtils.find("{\\blur4}{\\an8}你說什麼？", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{asd\\blur4}{\\an8}你說什麼？", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{asd\\blur4}  {an8}你說什麼？", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{\\blur4}{\\pos(944.855,104.142)}不要", REG_TEXT_CODE));
		System.out.println(XRegularUtils.find("{\\fs35\\bord0\\c&HDDB6AC&\\frz353.6\\pos(824.001,482)}心愛的來信", REG_TEXT_CODE));
		System.out.println("{\\fs35\\bord0\\c&HDDB6AC&\\frz353.6\\pos(824.001,482)}心愛的來信".replaceAll(REG_TEXT_CODE, ""));
		System.out.println("{\\blur4}{\\pos(944.855,104.142)}不要".replaceAll(REG_TEXT_CODE, ""));
		System.out.println("{\\fs75\\blur2.25\\bord1.5\\an5\\c&H082235&\\b1\\3c&H13487A&\\pos(958.629,788.572)\\1a&H00&}悲哀的演員".replaceAll(REG_TEXT_CODE, ""));
		System.out.println("{\\an5\\fs30\\pos(829.715,958.285)}的手下".replaceAll(REG_TEXT_CODE, ""));

		System.out.println("0,0:23:38.50,0:23:39.74,兔子第一季中文藍光,,0,0,0,,{\\blur4}{\\pos(944.855,104.142)}不要".split(",", 10)[9]);
	}

	private String[] getTrimedKeyValue(String line) {
		String[] result = null;
		int colonIndex = line.indexOf(":");
		if (colonIndex > 0) {
			result = new String[2];
			result[0] = line.substring(0, colonIndex).trim();
			result[1] = colonIndex + 1 >= line.length() ? "" : line.substring(colonIndex + 1).trim();
		}

		return result;
	}

	private boolean isSectionFlg(String trimedLine) {
		return trimedLine.matches("\\[.+\\]");
	}

	@Override
	public boolean isDialogue(String line) {
		return super.isDialogue(line);
	}
}
