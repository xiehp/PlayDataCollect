package xie.playdatacollect.collector.quartz.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xie.quartz.trigger")
public class XCronConfig {
	public static final String PER_02_SECOND = "0/2 * * * * ? *";
	public static final String PER_03_SECOND = "0/3 * * * * ? *";
	public static final String PER_10_SECOND = "0/10 * * * * ? *";
	public static final String PER_15_SECOND = "0/15 * * * * ? *";
	public static final String PER_01_MIN = "0 0/1 * * * ? *";
	public static final String PER_05_MIN = "0 0/5 * * * ? *";
	public static final String PER_10_MIN = "0 0/10 * * * ? *";
	public static final String PER_01_HOUR = "0 0 0/1 * * ? *";
	public static final String PER_05_HOUR = "0 0 0/5 * * ? *";
	public static final String PER_10_HOUR = "0 0 0/10 * * ? *";
	public static final String PER_12_HOUR = "0 0 0/12 * * ? *";
}