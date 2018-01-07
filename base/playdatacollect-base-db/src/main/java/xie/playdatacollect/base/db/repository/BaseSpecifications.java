/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package xie.playdatacollect.base.db.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 转换检索数据
 */
public class BaseSpecifications {

	/**
	 * 将检索数据转换为Specification
	 * 
	 * @param filters 检索数据
	 * @param entityClazz 检索数据的类型
	 * @return Specification
	 */
	public static <T> Specification<T> bySearchFilter(final Collection<BaseSearchFilter> filters, final Class<T> entityClazz) {

		return new Specification<T>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters.size() > 0) {

					List<Predicate> predicates = new ArrayList<>();
					for (BaseSearchFilter filter : filters) {
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						List<Object> listValue = new ArrayList<>();
						if (filter.value instanceof List) {
							listValue = (List<Object>) filter.value;
						} else {
							listValue.add(filter.value);
						}

						for (Object value : listValue) {
							// logic operator
							switch (filter.operator) {
							case EQ:
								predicates.add(builder.equal(expression, value));
								break;
							case NE:
								predicates.add(builder.notEqual(expression, value));
								break;
							case LIKE:
								predicates.add(builder.like(expression, "%" + value + "%"));
								break;
							case GT:
								predicates.add(builder.greaterThan(expression, (Comparable) value));
								break;
							case LT:
								predicates.add(builder.lessThan(expression, (Comparable) value));
								break;
							case GTE:
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) value));
								break;
							case LTE:
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) value));
								break;
							case IN:
								if (filter.value instanceof Collection) {
									Collection<?> collectoin = (Collection<?>) filter.value;
									predicates.add(expression.in(collectoin));
								} else if (filter.value instanceof Object[]) {
									Object[] array = (Object[]) filter.value;
									predicates.add(expression.in(array));
								} else if (filter.value instanceof String) {
									String str = (String) filter.value;
									Object[] array = str.split(",");
									predicates.add(expression.in(array));
								} else {
									throw new IllegalArgumentException("IN 操作对应的值无法认识，请传入Collection或者Object[]数组或者逗号分隔字符串");
								}
								break;
							case ISNULL:
								predicates.add(builder.isNull(expression));
								break;
							case ISNOTNULL:
								predicates.add(builder.isNotNull(expression));
								break;
							}
						}
					}

					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
}
