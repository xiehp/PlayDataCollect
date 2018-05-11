package xie.playdatacollect.collector.quartz.job.youku;

import org.springframework.stereotype.Service;
import xie.playdatacollect.collector.quartz.job.base.BasePlayDataProcessUrlJob;
import xie.playdatacollect.collector.workflow.get.GetDataProcessIqiyi;
import xie.playdatacollect.collector.workflow.get.GetDataProcessYouku;
import xie.playdatacollect.collector.workflow.get.IGetDataProcess;
import xie.playdatacollect.collector.workflow.use.IUseDataProcess;
import xie.playdatacollect.collector.workflow.use.UseDataProcess;
import xie.playdatacollect.core.db.utils.AllDaoUtil;

import javax.annotation.Resource;

@Service
public class YoukuPlayDataProgramJob extends BasePlayDataProcessUrlJob {

	@Resource
	UseDataProcess useDataProcess;

	@Resource
	GetDataProcessYouku getDataProcessYouku;

//	@Override
//	public void executeJob(JobExecutionContext context) {
//		runSpider();
//	}

	@Override
	protected IGetDataProcess getIGetDataProcess() {
		return getDataProcessYouku;
	}

	@Override
	protected IUseDataProcess getIUseDataProcess() {
		return useDataProcess;
	}
}
