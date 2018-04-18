package xie.common.utils.constant;

import java.io.File;

public class XConst {

	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";

	public static final String separator = File.separator;

	public static final String IMAGE_TYPE_JPG = ".jpg";
	public static final String IMAGE_TYPE_GIF = ".gif";
	public static final String IMAGE_TYPE_PNG = ".png";

	public static final String CONTENT_TYPE_AMF = "application/x-amf";
	public static final String CONTENT_TYPE_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
	public static final String CHARSET_ISO_GBK = "GBK";
	public static final String CHARSET_ISO_GB2312 = "GB2312";
	public static final String CHARSET_ISO_ASCII = "ASCII";

	public static final int CONNECT_TIMEOUT_REQUEST = 20000;
	public static final int CONNECT_TIMEOUT_RESPONSE = 25000;

	public static final String COOKIES_PHP_SESSIONID = "PHPSESSID";

	public static final String RESPONSE_STATUS_LINE_SUCCESS = "HTTP/1.1 200 OK";

	public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";

	public static final char CHAR_04 = 04;

	public static final String ACTIONMESSAGE_FLAG_SUCCESS = "1";
	public static final String ACTIONMESSAGE_FLAG_FAILED = "2";

	public static final long SECOND_00_SEC = 0;
	/** 1秒 单位（秒） */
	public static final long SECOND_01_SEC = 1;
	/** 2秒 单位（秒） */
	public static final long SECOND_02_SEC = 2;
	/** 5秒 单位（秒） */
	public static final long SECOND_05_SEC = 5;
	/** 10秒 单位（秒） */
	public static final long SECOND_10_SEC = 10;
	/** 15秒 单位（秒） */
	public static final long SECOND_15_SEC = 15;
	/** 19秒 单位（秒） */
	public static final long SECOND_19_SEC = 19;
	public static final long SECOND_20_SEC = 20;
	public static final long SECOND_30_SEC = 30;
	public static final long SECOND_01_MIN = 60;
	public static final long SECOND_02_MIN = 120;
	public static final long SECOND_03_MIN = 180;
	public static final long SECOND_05_MIN = 300;
	/** 600秒 单位（秒） */
	public static final long SECOND_10_MIN = 600;
	public static final long SECOND_20_MIN = 1200;
	public static final long SECOND_30_MIN = 1800;
	/** 3600秒 单位（秒） */
	public static final long SECOND_01_HOUR = 3600;
	public static final long SECOND_02_HOUR = 7200;
	public static final long SECOND_05_HOUR = 18000;
	/** 36000秒 单位（秒） */
	public static final long SECOND_10_HOUR = 36000;
	/** 86400秒 单位（秒） */
	public static final long SECOND_24_HOUR = 86400;
	public static final long SECOND_02_DAY = SECOND_24_HOUR * 2;
	public static final long SECOND_03_DAY = SECOND_24_HOUR * 3;

	public static final String PROPERTIES_VALUE_TRUE = "1";
	public static final String PROPERTIES_VALUE_FALSE = "0";

	public static final long NOW_TIME_RATE = 10000;

	public static final int FLAG_INT_YES = 1;
	public static final int FLAG_INT_NO = 0;
	public static final Integer FLAG_INTEGER_YES = 1;
	public static final Integer FLAG_INTEGER_NO = 0;

}
