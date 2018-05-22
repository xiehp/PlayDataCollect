package xie.playdatacollect.core.db.utils;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.db.service.url.ProcessUrlService;

import javax.annotation.Resource;

@Component
public class AllServiceUtil {
	@Resource
	ProcessUrlService processUrlService;

	@Resource
	ProcessUrlUtils processUrlUtils;

	public ProcessUrlService getProcessUrlService() {
		return processUrlService;
	}

	public ProcessUrlUtils getProcessUrlUtils() {
		return processUrlUtils;
	}
}
