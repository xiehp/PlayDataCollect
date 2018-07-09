package xie.playdatacollect.front.controller.vo;

import java.time.Instant;

public class IndexPlayDayaVo {

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

	public void setPreMonthPlayTime(Instant preMonthPlayTime) {
		this.preMonthPlayTime = preMonthPlayTime;
	}
}
