package xie.common.spring.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseRepositoryPlus<T> {

	public abstract EntityManager getEntityManager();

	@SuppressWarnings("unchecked")
	public <M> PageImpl<M> getResult(String queryHql, String countHql, Map<String, Object> map, PageRequest pageRequest) {
		Query queryString = getEntityManager().createQuery(queryHql);
		Query queryCount = getEntityManager().createQuery(countHql);

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			queryString.setParameter(key, map.get(key));
			queryCount.setParameter(key, map.get(key));
		}

		Long count = null;
		if (pageRequest != null) {
			int first = pageRequest.getPageNumber() * pageRequest.getPageSize();
			queryString.setFirstResult(first);
			queryString.setMaxResults(pageRequest.getPageSize());
			count = (Long) queryCount.getSingleResult();
		}

		List<M> list = null;
		if (count != null && count == 0) {
			list = new ArrayList<>();
		} else {
			list = queryString.getResultList();
		}

		if (pageRequest != null) {
			return new PageImpl<M>(list, pageRequest, count);
		} else {
			return new PageImpl<M>(list);
		}
	}

	@SuppressWarnings("unchecked")
	public <M> PageImpl<M> getResultNativeSql(String queryHql, String countHql, Map<String, Object> map, PageRequest pageRequest) {
		Query queryString = getEntityManager().createNativeQuery(queryHql);
		Query queryCount = getEntityManager().createNativeQuery(countHql);

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			queryString.setParameter(key, map.get(key));
			queryCount.setParameter(key, map.get(key));
		}

		BigInteger count = null;
		if (pageRequest != null) {
			int first = pageRequest.getPageNumber() * pageRequest.getPageSize();
			queryString.setFirstResult(first);
			queryString.setMaxResults(pageRequest.getPageSize());
			count = (BigInteger) queryCount.getSingleResult();
		}

		List<M> list = null;
		if (count != null && count.equals(0)) {
			list = new ArrayList<>();
		} else {
			list = queryString.getResultList();
		}

		if (pageRequest != null) {
			return new PageImpl<M>(list, pageRequest, count.longValueExact());
		} else {
			return new PageImpl<M>(list);
		}
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public Page<String> geIdResult(String queryHql, String countHql, Map<String, Object> map, PageRequest pageRequest) {
		Query queryString = getEntityManager().createQuery(queryHql);
		Query queryCount = getEntityManager().createQuery(countHql);

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			queryString.setParameter(key, map.get(key));
			queryCount.setParameter(key, map.get(key));
		}

		int first = pageRequest.getPageNumber() * pageRequest.getPageSize();
		queryString.setFirstResult(first);
		queryString.setMaxResults(pageRequest.getPageSize());

		Long count = (Long) queryCount.getSingleResult();
		List<String> list = queryString.getResultList();

		return new PageImpl<String>(list, pageRequest, count);
	}

	public <M> Page<M> createEmptyPage(PageRequest pageRequest) {
		List<M> list = new ArrayList<>();
		if (pageRequest == null) {
			return new PageImpl<M>(list);
		} else {
			return new PageImpl<M>(list, pageRequest, 0);
		}
	}
}
