/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package xie.common.spring.jpa.repository;

import org.springframework.util.StringUtils;
import xie.common.utils.date.XDateUtil;
import xie.common.utils.string.XStringUtils;
import xie.common.utils.validate.XValidatorUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 页面检索条件的处理
 */
public class BaseSearchFilter {

	/**
	 * 检索操作符的枚举
	 */
	public enum BaseOperator {
		/** 等于（= value） */
		EQ,
		/** 不等于（!= value） */
		NE,
		/** 相似（like %value%） */
		LIKE,
		/** 大于（> value） */
		GT,
		/** 小于（< value） */
		LT,
		/** 大于等于（>= value） */
		GTE,
		/** 小于等于（<= value） */
		LTE,
		/** IN（in (value1, value2, ...)） 注：value需要以Collection形式传递 */
		IN,
		/**  */
		ISNULL,
		/**  */
		ISNOTNULL

		;

		public String getStr(String columnName) {
			return name() + "_" + columnName;
		}

		public void put(Map<String, Object> searchParams, String columnName, Object value) {
			String key = getStr(columnName);
			searchParams.put(key, value);
		}
	}

	/** 检索字段 */
	public String fieldName;
	/** 检索值 */
	public Object value;
	/** 检索操作符 */
	public BaseOperator operator;

	public BaseSearchFilter(String fieldName, BaseOperator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * 将Operator_searchColumnName形式的检索条件转化为SearchFilter<br>
	 * 检索字段的key的形式为"操作符_字段名"或"操作符_字段路径"<br>
	 * 如EQ_companyName<br>
	 * 如LIKE_industry.industryName<br>
	 */
	public static Map<String, BaseSearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, BaseSearchFilter> filters = new HashMap<>();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (XValidatorUtils.isNull(value)) {
				continue;
			}

			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			BaseOperator operator = BaseOperator.valueOf(names[0]);

			BaseSearchFilter filter = new BaseSearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}

	/**
	 * 转换日期，<br>
	 * 将yyyy-MM-dd的字符串形式的时间转换为日期形式<br>
	 */
	public static void convertDate(Map<String, Object> searchParams, String searchKey) throws Exception {
		String startDateStr = (String) searchParams.get(searchKey);
		if (XValidatorUtils.isNotNull(startDateStr)) {
			Date startDate = XDateUtil.convertFromString(startDateStr, XDateUtil.YMD1);
			searchParams.put(searchKey, startDate);
		}
	}

	/**
	 * 转换结束日期，<br>
	 * 将yyyy-MM-dd的字符串形式的时间转换为日期形式，并且在该时间基础上增加1天<br>
	 */
	public static void convertEndDate(Map<String, Object> searchParams, String searchKey) throws Exception {
		String endDateStr = (String) searchParams.get(searchKey);
		if (XValidatorUtils.isNotNull(endDateStr)) {
			Date endDate = XDateUtil.convertFromString(endDateStr, XDateUtil.YMD1);
			endDate = XDateUtil.seekDate(endDate, 1);
			searchParams.put(searchKey, endDate);
		}
	}

	/**
	 * 适用于精度为日(yyyyMMdd)，形式为>=startDate AND <=endDate 的开始和结束日
	 * 
	 * @param startDateName 开始日期的key
	 * @param endDateName 结束日期的key
	 */
	public static void convertStartAndEndDate(
			Map<String, Object> searchParams,
			String startDateName, String endDateName) throws ParseException {

		if (searchParams == null) {
			return;
		}

		// 开始日期
		if (!XStringUtils.isBlank(startDateName) && searchParams.get(startDateName) != null) {
			Object startDateObj = searchParams.get(startDateName);
			Date startDate = XDateUtil.fromString(startDateObj);
			startDate = XDateUtil.getStartTimeOfDay(startDate);
			searchParams.put(startDateName, startDate);
		}

		// 结束日期
		if (!XStringUtils.isBlank(endDateName) && searchParams.get(endDateName) != null) {
			Object endDateObj = searchParams.get(endDateName);
			Date endDate = XDateUtil.fromString(endDateObj);
			endDate = XDateUtil.getEndTimeOfDay(endDate);
			searchParams.put(endDateName, endDate);
		}
	}
}
