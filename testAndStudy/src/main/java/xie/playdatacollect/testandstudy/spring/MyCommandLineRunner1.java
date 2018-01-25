package xie.playdatacollect.testandstudy.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import xie.playdatacollect.core.entity.MetricEntity;
import xie.playdatacollect.core.entity.TagEntity;
import xie.playdatacollect.core.service.MetricService;
import xie.playdatacollect.core.service.TagService;
import xie.playdatacollect.core.service.ValueService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于初始化一些基础数据
 */
@Configuration
public class MyCommandLineRunner1 implements CommandLineRunner {

	@Resource
	TagService tagService;
	@Resource
	MetricService metricService;
	@Resource
	ValueService valueService;

	@Override
	public void run(String... args) {
		try {
			System.out.println("MyCommandLineRunner1 start");

			tagService.insertNewKeyName("bilibili", "bilibili", TagEntity.class);
			tagService.insertNewKeyName("youku", "优酷", TagEntity.class);
			tagService.insertNewKeyName("iqiyi", "爱奇艺", TagEntity.class);

			metricService.insertNewKeyName("play", "播放量", MetricEntity.class);
			metricService.insertNewKeyName("fans", "追番量", MetricEntity.class);
			metricService.insertNewKeyName("review", "弹幕量", MetricEntity.class);

			Map<String, String> map = new HashMap<>();
			System.out.println(metricService.findByKey("review").copyTo(map));
			System.out.println("MyCommandLineRunner1 end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}