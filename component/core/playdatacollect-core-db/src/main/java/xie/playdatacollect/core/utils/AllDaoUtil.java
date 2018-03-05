package xie.playdatacollect.core.utils;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.dao.ProcessUrlDao;
import xie.playdatacollect.core.dao.SourcesDao;

import javax.annotation.Resource;

@Component
public class AllDaoUtil {
	@Resource
	SourcesDao sourcesDao;
	@Resource
	ProcessUrlDao processUrlDao;

	public SourcesDao getSourcesDao() {
		//
		return sourcesDao;
	}

	public ProcessUrlDao getProcessUrlDao() {
		return processUrlDao;
	}
}
