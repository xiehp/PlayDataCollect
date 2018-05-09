package xie.playdatacollect.collector.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

public abstract class XBaseQuartzJobBean extends QuartzJobBean {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private String name;

	@Resource
	protected RestTemplate restTemplate;

	/**
	 * Invoked if a Job data map entry with that name<br>
	 * 通过job的参数自动塞入数值
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LinkedHashMap<String,Object> map = new LinkedHashMap<>();
		if (context.getMergedJobDataMap() != null) {
			context.getMergedJobDataMap().forEach((key, value) ->{
				map.put(key, value);
			});
		}

		logger.info("Start {}({}) by {}, Params:{}", this.getClass().getSimpleName(), name, context.getTrigger(), map);

		try {
			executeJob(context);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + "Error {} by {}, {}", name, context.getTrigger(), e.getMessage());
			throw new JobExecutionException(e);
		} finally {
			logger.info("End {}({}) by {}", this.getClass().getSimpleName() + name, context.getTrigger());
		}
	}

	public abstract void executeJob(JobExecutionContext context) throws JobExecutionException, InterruptedException;
}
