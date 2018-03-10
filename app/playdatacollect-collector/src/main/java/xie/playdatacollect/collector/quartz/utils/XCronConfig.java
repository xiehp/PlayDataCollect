package xie.playdatacollect.collector.quartz.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xie.quartz.trigger")
public class XCronConfig {
	public static final String PER_01_MIN = "0 0/1 * * * ? *";
	public static final String PER_05_MIN = "0 0/5 * * * ? *";
	public static final String PER_10_MIN = "0 0/10 * * * ? *";
	public static final String PER_01_HOUR = "0 0 0/1 * * ? *";
	public static final String PER_05_HOUR = "0 0 0/5 * * ? *";
	public static final String PER_10_HOUR = "0 0 0/10 * * ? *";
}
