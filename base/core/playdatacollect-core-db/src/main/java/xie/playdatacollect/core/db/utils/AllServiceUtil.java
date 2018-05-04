package xie.playdatacollect.core.db.utils;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.db.service.ProcessUrlService;

import javax.annotation.Resource;

@Component
public class AllServiceUtil {
	public ProcessUrlService getProcessUrlService() {
		return processUrlService;
	}

	@Resource
	ProcessUrlService processUrlService;

}
