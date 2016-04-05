package xie.common.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XStringUtils {

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
	 * @param d 切分字符
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
		return arrayToString(list.toArray(new String[] {}));
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
	 * @param d 分隔�?
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

		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < array.length; i++) {
			String str = array[i];
			sb.append(str);
			if (i != array.length - 1) {
				sb.append(d);
			}
		}

		return sb.toString();
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
	 * 
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
	public static List formatStr(List list, String[] paramArray) {
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
	 * 替换文本 "A[[0]]AABB[[1]]B",[C,D] --> "ACAABBDB"
	 */
	public static Map formatStr(Map map, String[] paramArray) {
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
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
			SimpleDateFormat sdf = new SimpleDateFormat();
			try {
				return (X) sdf.parse(str);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("不支持的转换类型:" + clazz);
		}
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
