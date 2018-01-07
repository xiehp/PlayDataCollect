package xie.playdatacollect.base.db.repository;

import xie.playdatacollect.base.db.repository.BaseSearchFilter.BaseOperator;

import java.util.HashMap;
import java.util.Map;

public class BaseSearchParams {
	Map<String, Object> searchParams;

	public BaseSearchParams() {
		searchParams = new HashMap<>();
	}

	public BaseSearchParams(Map<String, Object> searchParams) {
		if (searchParams == null) {
			searchParams = new HashMap<>();
		}
		this.searchParams = searchParams;
	}

	public Map<String, Object> getParams() {
		return searchParams;
	}

	public BaseSearchParams EQ(String columnName, Object value) {
		BaseOperator.EQ.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams NE(String columnName, Object value) {
		BaseOperator.NE.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams LIKE(String columnName, Object value) {
		BaseOperator.LIKE.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams GT(String columnName, Object value) {
		BaseOperator.GT.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams LT(String columnName, Object value) {
		BaseOperator.LT.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams GTE(String columnName, Object value) {
		BaseOperator.GTE.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams LTE(String columnName, Object value) {
		BaseOperator.LTE.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams IN(String columnName, Object value) {
		BaseOperator.IN.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams ISNULL(String columnName, Object value) {
		BaseOperator.ISNULL.put(searchParams, columnName, value);
		return this;
	}

	public BaseSearchParams XXXX(String columnName, Object value) {
		BaseOperator.ISNOTNULL.put(searchParams, columnName, value);
		return this;
	}
}
