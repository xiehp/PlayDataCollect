package xie.common.utils.string;

import xie.common.utils.date.XDateUtil;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XStringUtils {

	/**
	 * 默认false，特殊字符解释为true
	 *
	 * @param value
	 * @return "1","on","yes","true",返回true
	 */
	public static boolean parseToBoolean(String value) {
		if ("1".equalsIgnoreCase(value)) {
			return true;
		}
		if ("on".equalsIgnoreCase(value)) {
			return true;
		}
		if ("yes".equalsIgnoreCase(value)) {
			return true;
		}
		if ("true".equalsIgnoreCase(value)) {
			return true;
		}

		return false;
	}

	public static void println(Object object) {
		if (object == null) {
			System.out.println(object);
			return;
		}

		if (object instanceof Map) {
			Map<?, ?> objectMap = (Map<?, ?>) object;
			System.out.println(objectMap + ", size: " + objectMap.size());
			for (Object key : objectMap.keySet()) {
				System.out.println(key + ":" + objectMap.get(key));
			}
		}
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isBlank(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	public static boolean isNotBlank(String value) {
		return !isBlank(value);
	}

	public static String nullToBlank(String value) {
		if (value == null) {
			value = "";
		}
		return value;
	}

	public static String subOrAddZero(int value, int len) {
		String str = String.valueOf(value);
		if (str.length() > len) {
			return str.substring(str.length() - len);
		}

		while (str.length() < len) {
			str = "0" + str;
		}

		return str;
	}

	public static String addZero(int value, int len) {
		String str = String.valueOf(value);

		while (str.length() < len) {
			str = "0" + str;
		}

		return str;
	}

	public static String subString(String subStr, String begin, String end) {
		// int indexBegin = "".equals(begin) ? 0 : subStr.indexOf(begin);
		// if (indexBegin == -1) {
		// return null;
		// }
		// indexBegin = indexBegin + begin.length();
		//
		// int indexEnd = subStr.indexOf(end, indexBegin);
		// if (indexEnd <= indexBegin) {
		// return null;
		// }
		//
		// return subStr.substring(indexBegin, indexEnd);

		return subString(subStr, 0, begin, end);
	}

	public static String subString(String subStr, int startIndex, String begin, String end) {
		int indexBegin = "".equals(begin) ? startIndex : subStr.indexOf(begin, startIndex);
		if (indexBegin == -1) {
			return null;
		}
		indexBegin = indexBegin + begin.length();

		int indexEnd = subStr.indexOf(end, indexBegin);
		if (indexEnd <= indexBegin) {
			return null;
		}

		return subStr.substring(indexBegin, indexEnd);
	}

	/**
	 * 字符串转换成数组�? 默认用�?�号区分，如果为空，
	 *
	 * @param key
	 * @return
	 * @since 2014-2-15下午12:33:45
	 */
	public static String[] getArray(String value) {
		return getArray(value, ",");
	}

	/**
	 * 字符串转换成数组
	 *
	 * @param key
	 * @param d   切分字符
	 * @return
	 * @since 2014-2-15下午12:34:14
	 */
	public static String[] getArray(String value, String d) {
		if (isBlank(value)) {
			return new String[0];
		}

		return value.trim().split(d);
	}

	/**
	 * 返回"str1,str2,str3"形式的字符串
	 *
	 * @param list
	 * @return
	 * @since 2015-4-2 下午10:56:13
	 */
	public static String arrayToString(List<String> list) {
		return arrayToString(list.toArray(new String[]{}));
	}

	/**
	 * 返回"str1,str2,str3"形式的字符串
	 *
	 * @param array
	 * @return
	 * @since 2015-4-2 下午10:56:13
	 */
	public static String arrayToString(String[] array) {
		return arrayToString(array, ",");
	}

	/**
	 * 将List转换成字符串形式
	 *
	 * @param array
	 * @param d     分隔�?
	 * @return
	 * @since 2015-8-24 下午9:36:33
	 */
	public static String arrayToString(String[] array, String d) {
		if (array == null) {
			return "";
		}

		if (array.length == 0) {
			return "";
		}
		return String.join(",", array);
//		StringBuilder sb = new StringBuilder("");
//		for (int i = 0; i < array.length; i++) {
//			String str = array[i];
//			sb.append(str);
//			if (i != array.length - 1) {
//				sb.append(d);
//			}
//		}
//
//		return sb.toString();
	}

	/**
	 * string中是否有以startArray�?头的字符�?
	 *
	 * @param string
	 * @param startArray
	 * @return
	 * @since 2014-10-28上午12:00:09
	 */
	public static boolean startWith(String string, String[] startArray) {
		for (String startStr : startArray) {
			if (string.startsWith(startStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * string中是否包含有containArray的字符串
	 *
	 * @param string
	 * @param containArray
	 * @return
	 * @since 2014-10-28上午12:05:23
	 */
	public static boolean containWith(String string, String[] containArray) {
		for (String startStr : containArray) {
			if (string.contains(startStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * string中是否包含有containArray的字符串
	 *
	 * @param string
	 * @param containArray
	 * @return
	 * @since 2014-10-28上午12:05:23
	 */
	public static boolean containWithIgnoreCase(String string, String[] containArray) {
		if (string == null) {
			return false;
		}

		for (String startStr : containArray) {
			if (startStr == null) {
				continue;
			}
			if (string.toLowerCase().contains(startStr.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串转换unicode<br>
	 * 默认英文和数字不进行转换<br>
	 *
	 * @param string
	 * @return
	 * @since 2014-11-12上午1:42:31
	 */
	public static String string2Unicode(String string) {
		return string2Unicode(string, false);
	}

	/**
	 * 字符串转换unicode
	 *
	 * @param string
	 * @param escapeNumberOrLetterFlg 是否转换英文或�?�数�?
	 * @return
	 * @since 2014-11-12上午1:41:55
	 */
	public static String string2Unicode(String string, boolean escapeNumberOrLetterFlg) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字�?
			char c = string.charAt(i);

			if (!escapeNumberOrLetterFlg) {
				if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)) {
					unicode.append(c);
					continue;
				}
			}

			// 转换为unicode
			String unicodeStr = Integer.toHexString(c);
			if (unicodeStr == null) {
				unicode.append(c);
			} else if (unicodeStr.length() == 1) {
				unicode.append("\\u000" + Integer.toHexString(c));
			} else if (unicodeStr.length() == 2) {
				unicode.append("\\u00" + Integer.toHexString(c));
			} else if (unicodeStr.length() == 3) {
				unicode.append("\\u0" + Integer.toHexString(c));
			} else if (unicodeStr.length() == 4) {
				unicode.append("\\u" + Integer.toHexString(c));
			} else {
				unicode.append(c);
			}
		}
		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	/**
	 * 替换文本 "A[[0]]AABB[[1]]B",[C,D] --> "ACAABBDB"
	 */
	public static List<Object> formatStr(List<Object> list, String[] paramArray) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object value = list.get(i);
				if (value instanceof String) {
					String formattedValue = formatStr((String) value, paramArray);
					list.set(i, formattedValue);
				}
			}
		}

		return list;
	}

	/**
	 * 将Map中的值进行文本替换<br>
	 * 替换方式：[[0]] -> <br>
	 * "A[[0]]B + [啊] --> "A啊B"<br>
	 * "A[[0]]AABB[[1]]B" + [啊,哦] --> "A啊AABB哦B"<br>
	 *
	 * @param map        需要替换的数值
	 * @param paramArray 替换参数
	 * @return
	 */
	public static Map<String, Object> formatStr(Map<String, Object> map, String[] paramArray) {
		if (map != null && map.size() > 0) {
			for (String key : map.keySet()) {
				Object value = map.get(key);
				if (value instanceof String) {
					String formattedValue = formatStr((String) value, paramArray);
					map.put(key, formattedValue);
				}
			}
		}

		return map;
	}

	/**
	 * 替换文本 "A[[0]]AABB[[1]]B",[C,D] --> "ACAABBDB"
	 */
	public static String formatStr(String formatStr, String[] paramArray) {
		if (formatStr == null || formatStr.length() == 0) {
			return formatStr;
		}

		if (paramArray == null || paramArray.length == 0) {
			return formatStr;
		}

		String returnStr = formatStr;
		for (int i = 0; i < paramArray.length; i++) {
			String param = paramArray[i];
			returnStr = returnStr.replaceAll("\\[\\[" + i + "\\]\\]", param);
		}

		return returnStr;
	}

	/**
	 * 首字母变大写
	 *
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str) {
		if (str == null || str.length() < 1) {
			return str;
		}

		if (str.length() == 1) {
			return str.toUpperCase();
		}

		String firstLetter = str.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();

		return firstLetter + str.substring(1, str.length());
	}

	/**
	 * 将String转换成另外一种对象
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <X> X convert(String str, Class<X> clazz) {
		if (str == null) {
			return null;
		}
		if (XStringUtils.isBlank(str)) {
			if (clazz != String.class) {
				return null;
			}
		}

		if (clazz == String.class) {
			return (X) str;
		} else if (clazz == Integer.class) {
			return (X) Integer.valueOf(str);
		} else if (clazz == Long.class) {
			return (X) Long.valueOf(str);
		} else if (clazz == Date.class) {
			SimpleDateFormat sdf = new SimpleDateFormat(XDateUtil.YMD_FULL);
			try {
				return (X) sdf.parse(str);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("不支持的转换类型:" + clazz);
		}
	}

	/**
	 * 替换字符串中字符 例子： 身份证中间留9个，前后为3个*： str="330112197810115231" originalFromLast="false" star="5" end="14" replaceNumStar="3" replaceMiddle="false" 结果： ***219781011*** 银行卡留后4位，前面4个*： str="12345678980567865" start="4" replaceNumStar="4" 结果； ****7865 邮箱： str="kenst.xu@excel-gits.com" start="1" end=”-6”
	 * posFromLast="false" replaceNumStar="4" 结果： k****ts.com
	 *
	 * @param str            要处理的原始字符串
	 * @param startPos       开始位置，默认0
	 * @param endPos         结束位置，默认字符串的长度 -1为倒数
	 * @param posFromLast    计算位置的起点：true从字符串最右边开始算起，false从字符串左边开始计算位置
	 * @param replaceMiddle  是替换中间还是替换2头的字符串，true替换中间，false替换2头
	 * @param replaceNumStar 用几个“*”替换被替换的字符串, 默认值为1
	 * @param star           用来替换的字符，默认为"*"
	 * @return
	 */
	public static String hideChar(final String str, final Integer startPos, Integer endPos, final boolean posFromLast, final boolean replaceMiddle, final Integer replaceNumStar, final String star) {
		int start1 = 0;
		if (startPos != null) {
			start1 = startPos;
			if (start1 < 0) {
				start1 = 0;
			}
			if (start1 >= str.length()) {
				start1 = str.length();
			}
		}

		int end1 = str.length();
		if (endPos != null) {
			end1 = endPos;
			if (end1 < 0) {
				end1 = str.length() + end1;
			}
			if (end1 >= str.length()) {
				end1 = str.length();
			}
		}

		if (start1 > end1) {
			int tmp = end1;
			end1 = start1;
			start1 = tmp;
		}

		final int start;
		final int end;
		if (posFromLast) {
			start = str.length() - end1;
			end = str.length() - start1;
		} else {
			start = start1;
			end = end1;
		}

		String s = "";
		if (str.length() > 0) {
			if (replaceMiddle) {
				final String s1 = str.substring(0, start);
				final String s2 = replaceStrByStar(str.substring(start, end), replaceNumStar, star, true);
				final String s3 = str.substring(end);

				s = s1 + s2 + s3;
			} else {
				final String s1 = replaceStrByStar(str.substring(0, start), replaceNumStar, star, false);
				final String s2 = str.substring(start, end);
				final String s3 = replaceStrByStar(str.substring(end), replaceNumStar, star, false);

				s = s1 + s2 + s3;
			}
		}

		return s;
	}

	private static String replaceStrByStar(final String s, final Integer replaceNumStar, final String star, final boolean force) {
		String rtn;

		if ((!force) && s.length() == 0) {
			rtn = "";
		} else {
			if (replaceNumStar == null) {
				rtn = s.replaceAll("(.)", star);
			} else {
				int num = replaceNumStar;
				if (num <= 0) {
					num = 1;
				}
				rtn = getStar(num, star);
			}
		}

		return rtn;
	}

	private static String getStar(final int len, final String star) {
		final StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < len; i++) {
			sb.append(star);
		}
		return sb.toString();
	}

	/**
	 * 判断是否equal，包括null==null
	 */
	public static boolean equals(String value1, String value2) {
		if (value1 != null) {
			return value1.equals(value2);
		} else if (value2 != null) {
			return value2.equals(value1);
		} else {
			return true;
		}
	}

	/**
	 * 判断是否equal，包括null==null
	 */
	public static boolean equalsIgnoreCase(String value1, String value2) {
		if (value1 != null) {
			return value1.equalsIgnoreCase(value2);
		} else if (value2 != null) {
			return value2.equalsIgnoreCase(value1);
		} else {
			return true;
		}
	}

	/**
	 * 数组中是否存在某个字符串
	 */
	public static boolean existIgnoreCase(List<String> list, String value) {
		if (value == null) {
			return list.contains(value);
		}

		for (String str : list) {
			if (value.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将待删列表中除了保留列表中存在的文字，都删除掉
	 *
	 * @param removeList 待删列表
	 * @param leaveList  保留列表
	 * @return
	 */
	public static List<String> removeIfNotEqualIgnoreCase(List<String> removeList, List<String> leaveList) {
		if (removeList == null || removeList.size() == 0) {
			return removeList;
		}
		if (leaveList == null || leaveList.size() == 0) {
			return removeList;
		}

		Predicate<String> predicate = new Predicate<String>() {
			@Override
			public boolean test(String value) {
				if (XStringUtils.existIgnoreCase(leaveList, value)) {
					return false;
				} else {
					return true;
				}
			}
		};

		removeList.removeIf(predicate);
		return removeList;
	}

	public static String toString2(Object obj) {
		StringBuffer strBuf = new StringBuffer();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			strBuf.append(obj.getClass().getName());
			strBuf.append("(");
			for (int i = 0; i < fields.length; i++) {
				Field fd = fields[i];
				fd.setAccessible(true);
				strBuf.append(fd.getName() + ":");
				strBuf.append(fd.get(obj));
				fd.setAccessible(false);
				if (i != fields.length - 1)
					strBuf.append("|");
			}
			strBuf.append(")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		String aaa = "aaabbbccc1dddeeefff2ggghhhiii3jjjkkklll4mmmnnnooo5";
		System.out.println(subString(aaa, -1, "aaa", "kkk"));
		System.out.println(subString(aaa, 0, "aaa", "kkk"));
		System.out.println(subString(aaa, 1, "aaa", "kkk"));
		System.out.println(subString(aaa, 4, "ggg", "kkk"));
		System.out.println(subString(aaa, 14, "ggg", "kkk"));
		System.out.println(subString(aaa, 19, "ggg", "kkk"));
		System.out.println(subString(aaa, 20, "ggg", "kkk"));
		System.out.println(subString(aaa, 21, "ggg", "kkk"));
		System.out.println(subString(aaa, 22, "ggg", "kkk"));
		System.out.println(subString(aaa, 44, "ggg", "kkk"));

		String test = "张三aab~@012345678911啊abxyzABZY";
		String unicode = string2Unicode(test);
		String string = unicode2String(unicode + "1");
		System.out.println(unicode);
		System.out.println(string);

	}
}
