package xie.framework.core.service.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import xie.common.spring.jpa.repository.BaseDao;
import xie.common.spring.jpa.service.BasePageService;
import xie.common.utils.constant.XConst;
import xie.framework.core.exception.CodeApplicationException;
import xie.framework.core.service.dictionary.dao.PublicDictionaryDao;
import xie.framework.core.service.dictionary.entity.PublicDictionary;

import java.util.*;


@Service
public class PublicDictionaryService extends BasePageService<PublicDictionary, String> {

	private volatile Map<String, List<PublicDictionary>> dictionaryMap;

	private PublicDictionaryDao publicDictionaryDao;

	@Autowired
	public PublicDictionaryService(PublicDictionaryDao publicDictionaryDao) {
		this.publicDictionaryDao = publicDictionaryDao;
	}

	@Override
	public BaseDao<PublicDictionary, String> getBaseDao() {
		return publicDictionaryDao;
	}

	public Map<String, List<PublicDictionary>> getAllDictionaryMap() {
		buildMap();
		return dictionaryMap;
	}

	public void reloadCache() {
		dictionaryMap = null;
		buildMap();
	}

	public List<PublicDictionary> findByTypeId(final String typeId) {
		Sort sort = new Sort(Direction.ASC, "sort");
		return publicDictionaryDao.findByTypeId(typeId, sort);
	}

	private void buildMap() {
		if (dictionaryMap == null) {
			synchronized (PublicDictionaryService.class) {
				if (dictionaryMap == null) {
					dictionaryMap = new LinkedHashMap<>();
					Iterable<PublicDictionary> publicDictionaryList = publicDictionaryDao.findAll();
					final Iterator<PublicDictionary> it = publicDictionaryList.iterator();
					while (it.hasNext()) {
						final PublicDictionary publicDictionary = it.next();
						List<PublicDictionary> pkList = dictionaryMap.get(publicDictionary.getTypeId());
						if (pkList == null) {
							pkList = new ArrayList<>();
							dictionaryMap.put(publicDictionary.getTypeId(), pkList);
						}

						pkList.add(publicDictionary);
					}

				}
			}
		}
	}


	/**
	 * 修改记录状态 0变1 1变0
	 *
	 * @param id
	 * @throws CodeApplicationException
	 */
	public void editStatus(String id) throws CodeApplicationException {
		PublicDictionary publicDictionary = publicDictionaryDao.findById(id).orElse(null);
		if (publicDictionary == null) {
			throw new CodeApplicationException("系统错误,不存在此记录");
		}
		Integer status = publicDictionary.getStatus();

		if (status.equals(XConst.FLAG_INT_YES)) {
			publicDictionary.setStatus(XConst.FLAG_INT_NO);
		} else {
			publicDictionary.setStatus(XConst.FLAG_INT_YES);
		}

		publicDictionaryDao.save(publicDictionary);
	}


	/**
	 * 更新
	 *
	 * @param id
	 * @param code
	 * @param value
	 * @param describe
	 * @param sort
	 * @return
	 */
	public PublicDictionary update(String id, String typeId, String code, String value, String describe, String sort) {

		PublicDictionary publicDictionary = publicDictionaryDao.findById(id).orElse(null);

		publicDictionary.setTypeId(typeId);
		publicDictionary.setCode(code);
		publicDictionary.setValue(value);
		publicDictionary.setRemark(describe);
		if (sort != null) {
			publicDictionary.setSort(new Integer(sort));
		}
		publicDictionary.setStatus(XConst.FLAG_INT_YES);

		return publicDictionaryDao.save(publicDictionary);
	}


	/**
	 * 新增
	 *
	 * @param code
	 * @param value
	 * @param describe
	 * @param sort
	 * @return
	 */
	public PublicDictionary add(String typeId, String code, String value, String describe, String sort) {
		PublicDictionary publicDictionary = new PublicDictionary();

		publicDictionary.setTypeId(typeId);
		publicDictionary.setCode(code);
		publicDictionary.setValue(value);
		publicDictionary.setRemark(describe);
		if (sort != null) {
			publicDictionary.setSort(new Integer(sort));
		}
		publicDictionary.setStatus(XConst.FLAG_INT_YES);
		return publicDictionaryDao.save(publicDictionary);
	}
}
