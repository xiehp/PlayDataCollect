package xie.playdatacollect.front.controller.vo;

import xie.common.utils.date.XDateUtil;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class IndexPlayDataVo {

	public DateTimeFormatter FORMAT1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/** site + "_" + name */
	private String key;
	private String site; // bilibili iqiyi youku
	private String name;

	private Long nowPlayCount;
	private Instant nowPlayTime;

	private Long preHourPlayCount;
	private Instant preHourPlayTime;

	private Long preDayPlayCount;
	private Instant preDayPlayTime;

	private Long preWeekPlayCount;
	private Instant preWeekPlayTime;

	private Long preMonthPlayCount;
	private Instant preMonthPlayTime;

	private Long preYearPlayCount;
	private Instant preYearPlayTime;

	// function
	public Long getDiffCount(Long oldVal) {
		if (nowPlayCount == null || oldVal == null) {
			return null;
		}

		return nowPlayCount - oldVal;
	}

	public String getDiffCountFormat(Long oldVal) {
		Long val = getDiffCount(oldVal);
		return formatCount(val);
	}

	public static DecimalFormat decimalFormat = new DecimalFormat("#.00");
	public String formatCount(Long val) {
		if(val == null) {
			return "-";
		}

		if (val >= 10000) {
			if (val >= 100000000) {
				return decimalFormat.format(val / 100000000.0) + "亿";
			} else {
				return decimalFormat.format(val / 10000.0) + "万";
			}
		} else {
			return val.toString();
		}
	}

	// get set
	public String getKey() {
		return key;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNowPlayCount() {
		return nowPlayCount;
	}

	public void setNowPlayCount(Long nowPlayCount) {
		this.nowPlayCount = nowPlayCount;
	}

	public Instant getNowPlayTime() {
		return nowPlayTime;
	}

	public String getNowPlayTimeFormat() {
		return XDateUtil.formatTime(nowPlayTime, FORMAT1, "-");
	}

	public void setNowPlayTime(Instant nowPlayTime) {
		this.nowPlayTime = nowPlayTime;
	}

	public Long getPreHourPlayCount() {
		return preHourPlayCount;
	}

	public void setPreHourPlayCount(Long preHourPlayCount) {
		this.preHourPlayCount = preHourPlayCount;
	}

	public Instant getPreHourPlayTime() {
		return preHourPlayTime;
	}

	public String getPreHourPlayTimeFormat() {
		return XDateUtil.formatTime(preHourPlayTime, FORMAT1, "-");
	}

	public void setPreHourPlayTime(Instant preHourPlayTime) {
		this.preHourPlayTime = preHourPlayTime;
	}

	public Long getPreDayPlayCount() {
		return preDayPlayCount;
	}

	public void setPreDayPlayCount(Long preDayPlayCount) {
		this.preDayPlayCount = preDayPlayCount;
	}

	public Instant getPreDayPlayTime() {
		return preDayPlayTime;
	}

	public String getPreDayPlayTimeFormat() {
		return XDateUtil.formatTime(preDayPlayTime, FORMAT1, "-");
	}

	public void setPreDayPlayTime(Instant preDayPlayTime) {
		this.preDayPlayTime = preDayPlayTime;
	}

	public Long getPreWeekPlayCount() {
		return preWeekPlayCount;
	}

	public void setPreWeekPlayCount(Long preWeekPlayCount) {
		this.preWeekPlayCount = preWeekPlayCount;
	}

	public Instant getPreWeekPlayTime() {
		return preWeekPlayTime;
	}

	public String getPreWeekPlayTimeFormat() {
		return XDateUtil.formatTime(preWeekPlayTime, FORMAT1, "-");
	}

	public void setPreWeekPlayTime(Instant preWeekPlayTime) {
		this.preWeekPlayTime = preWeekPlayTime;
	}

	public Long getPreMonthPlayCount() {
		return preMonthPlayCount;
	}

	public void setPreMonthPlayCount(Long preMonthPlayCount) {
		this.preMonthPlayCount = preMonthPlayCount;
	}

	public Instant getPreMonthPlayTime() {
		return preMonthPlayTime;
	}

	public String getPreMonthPlayTimeFormat() {
		return XDateUtil.formatTime(preMonthPlayTime, FORMAT1, "-");
	}

	public void setPreMonthPlayTime(Instant preMonthPlayTime) {
		this.preMonthPlayTime = preMonthPlayTime;
	}

	public Long getPreYearPlayCount() {
		return preYearPlayCount;
	}

	public void setPreYearPlayCount(Long preYearPlayCount) {
		this.preYearPlayCount = preYearPlayCount;
	}

	public Instant getPreYearPlayTime() {
		return preYearPlayTime;
	}

	public String getPreYearPlayTimeFormat() {
		return XDateUtil.formatTime(preYearPlayTime, FORMAT1, "-");
	}

	public void setPreYearPlayTime(Instant preYearPlayTime) {
		this.preYearPlayTime = preYearPlayTime;
	}

}
