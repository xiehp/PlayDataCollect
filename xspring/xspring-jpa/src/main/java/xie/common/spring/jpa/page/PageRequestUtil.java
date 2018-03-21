package xie.common.spring.jpa.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import xie.common.spring.jpa.entity.BaseEntity;


import java.util.List;

public class PageRequestUtil {
	
	public static final String SORT_TYPE_AUTO = "auto";

	public static Order createOrder(String columnName, Direction direction) {
		Order order = new Order(direction, columnName);
		return order;
	}

	// public static createSort(Order... orders){
	// Sort sort = new Sort(orders);
	// return sort;
	// }

	/**
	 * 创建分页请求.
	 */
	public static PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		return buildPageRequest(pageNumber, pagzSize, sortType, null);
	}

	public static PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType, Direction direction) {

		Sort sort = null;
		if (SORT_TYPE_AUTO.equals(sortType)) {
			if (direction == null) {
				direction = Direction.DESC;
			}
			sort = new Sort(direction, BaseEntity.COLUMN_CREATE_DATE);
		} else if (sortType != null) {
			if (direction == null) {
				direction = Direction.ASC;
			}
			sort = new Sort(direction, sortType);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	public static PageRequest buildPageRequest(int pageNumber, int pagzSize, List<Order> orders) {
		Sort sort = new Sort(orders);

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	public static PageRequest buildPageRequest(int pageNumber, int pagzSize, Sort sort) {
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

}
