package xie.playdatacollect.collector.workflow.get;

import org.quartz.JobDataMap;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.core.db.entity.url.ProcessUrlEntity;

import java.util.List;

public interface IGetDataProcess {
	List<CollectedData> getDataList(List<ProcessUrlEntity> listProcessUrl, JobDataMap jobDataMap);
}
