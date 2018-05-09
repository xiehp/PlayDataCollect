package xie.playdatacollect.collector.quartz.job.bilibili;

import org.springframework.stereotype.Service;
import xie.playdatacollect.collector.quartz.job.base.BasePlayDataProcessUrlJob;
import xie.playdatacollect.collector.workflow.get.GetDataProcessBaseWebmagic;
import xie.playdatacollect.collector.workflow.get.IGetDataProcess;
import xie.playdatacollect.collector.workflow.use.IUseDataProcess;
import xie.playdatacollect.collector.workflow.use.UseDataProcess;

import javax.annotation.Resource;

@Service
public class BilibiliPlayDataProgramJob extends BasePlayDataProcessUrlJob {

//	@Resource
//	ProcessBilibili processBilibili;

	@Resource
	UseDataProcess useDataProcess;
	@Resource
	GetDataProcessBaseWebmagic getDataProcessWebmagicBilibili;


	@Override
	protected IGetDataProcess getIGetDataProcess() {
		return getDataProcessWebmagicBilibili;
	}

	@Override
	protected IUseDataProcess getIUseDataProcess() {
		return useDataProcess;
	}

}
