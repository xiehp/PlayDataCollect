package xie.framework.core.service.dictionary.utils;

import org.springframework.stereotype.Component;
import xie.framework.core.service.dictionary.dao.AutoQueueDao;
import xie.framework.core.service.dictionary.service.AutoQueueService;

import javax.annotation.Resource;

/**
 * 通过DB，用于生成序列数据
 */
@Component
public class AutoQueueUtils {

	@Resource
	private AutoQueueDao autoQueueDao;

	@Resource
	private AutoQueueService autoQueueService;

	public String nextValue(String code) {
		return null;
	}

	public long nextNumber(String code) {
		return autoQueueService.nextNumber(code);
	}
}
