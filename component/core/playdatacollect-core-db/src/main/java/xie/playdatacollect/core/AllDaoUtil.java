package xie.playdatacollect.core;

import org.springframework.stereotype.Component;
import xie.playdatacollect.core.dao.SourceDao;

import javax.annotation.Resource;

@Component
public class AllDaoUtil {
	@Resource
	SourceDao sourceDao;

	public SourceDao getSourceDao() {
		return sourceDao;
	}
}
