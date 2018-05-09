package xie.playdatacollect.collector.workflow.use;

import xie.playdatacollect.common.data.CollectedData;

import java.util.List;

public interface IUseDataProcess {
	void use(List<CollectedData> CollectedDataList, long collectTime);
}
