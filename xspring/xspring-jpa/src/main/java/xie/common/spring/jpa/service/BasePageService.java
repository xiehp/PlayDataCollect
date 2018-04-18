package xie.common.spring.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import xie.common.spring.jpa.entity.BaseEntity;
import xie.common.spring.jpa.entity.IdEntity;
import xie.common.spring.jpa.page.PageRequestUtil;
import xie.common.spring.jpa.repository.BaseSearchFilter;
import xie.common.spring.jpa.repository.BaseSpecifications;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BasePageService<M extends IdEntity, ID extends Serializable> extends BaseService<M, ID> {

	public Page<M> searchByPage(Map<String, Object> searchParams, int pageNumber, int defaultPageSize, String sortType, Class<? extends BaseEntity> clazz) {
		// 创建分页对象
		PageRequest pageRequest = PageRequestUtil.buildPageRequest(pageNumber, defaultPageSize, sortType);

		return searchByPage(searchParams, pageRequest, clazz);
	}

	@SuppressWarnings("unchecked")
	public Page<M> searchByPage(Map<String, Object> searchParams, PageRequest pageRequest, Class<? extends BaseEntity> clazz) {

		Map<String, BaseSearchFilter> filters = BaseSearchFilter.parse(searchParams);
		Specification<M> spec = (Specification<M>) BaseSpecifications.bySearchFilter(filters.values(), clazz);
		Page<M> list = getBaseDao().findAll(spec, pageRequest);

		return list;
	}

	public <N> Page<N> replaceNewList(Page<?> page, List<N> newList) {

		Pageable pageable = new PageRequest(page.getNumber(), page.getSize());
		Page<N> newPage = new PageImpl<>(newList, pageable, page.getTotalElements());

		return newPage;
	}
}
