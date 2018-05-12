package xie.playdatacollect.collector.quartz.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import xie.common.utils.date.DateUtil;

import java.text.ParseException;
import java.util.Date;

@ConfigurationProperties("xie.quartz.trigger")
public class XScheduleConfig {
	public static final String PER_01_SECOND = "0/1 * * * * ? *";
	public static final String PER_02_SECOND = "0/2 * * * * ? *";
	public static final String PER_03_SECOND = "0/3 * * * * ? *";
	public static final String PER_10_SECOND = "0/10 * * * * ? *";
	public static final String PER_15_SECOND = "0/15 * * * * ? *";
	public static final String PER_20_SECOND = "0/20 * * * * ? *";
	public static final String PER_25_SECOND = "0/25 * * * * ? *";
	public static final String PER_30_SECOND = "0/30 * * * * ? *";
	public static final String PER_40_SECOND = "0/40 * * * * ? *";
	public static final String PER_01_MIN = "0 0/1 * * * ? *";
	public static final String PER_02_MIN = "0 0/2 * * * ? *";
	public static final String PER_05_MIN = "0 0/5 * * * ? *";
	public static final String PER_10_MIN = "0 0/10 * * * ? *";
	public static final String PER_20_MIN = "0 0/20 * * * ? *";
	public static final String PER_30_MIN = "0 0/30 * * * ? *";
	public static final String PER_01_HOUR = "0 0 0/1 * * ? *";
	public static final String PER_05_HOUR = "0 0 0/5 * * ? *";
	public static final String PER_10_HOUR = "0 0 0/10 * * ? *";
	public static final String PER_12_HOUR = "0 0 0/12 * * ? *";

	public static final String VERSION_NAME = "1.4.3";
	public static Date VERSION_DATE = null;

	static {
		try {
			VERSION_DATE = DateUtil.fromString("2018-05-13 00:44");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
