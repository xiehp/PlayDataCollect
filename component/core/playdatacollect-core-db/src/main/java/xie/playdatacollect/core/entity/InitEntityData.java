package xie.playdatacollect.core.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import xie.common.date.DateUtil;
import xie.playdatacollect.core.AllDaoUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * 用于初始化一些基础数据
 */
@Configuration
public class InitEntityData implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(InitEntityData.class);

	@Resource
	AllDaoUtil allDaoUtil;

	@Override
	public void run(String... args) throws ParseException {
		log.info("InitEntityData start");

		// 网站信息
		saveData("bilibili", "哔哩哔哩弹幕网", "哔哩哔哩", "BL", "https://www.bilibili.com/");
		saveData("iqiyi", "爱奇艺网", "爱奇艺", "IQY", "https://www.iqiyi.com/");
		saveData("youku", "优酷视频网", "优酷", "YK", "https://www.youku.com/");
		saveData("qq", "腾讯视频", "腾讯视频", "TX", "https://v.qq.com/");
		saveData("sohu", "搜狐视频", "搜狐视频", "SH", "https://tv.sohu.com/");


		log.info("InitEntityData End");
	}


	private void saveData(String key, String name, String simpleName, String abName, String url) throws ParseException {
		String versionDef = "0.02";
		Date versionDate = DateUtil.fromString("2018-02-12 15:55:00");

		SourcesEntity sourcesEntity = allDaoUtil.getSourceDao().findByKeyword(key);
		if (sourcesEntity == null) {
			sourcesEntity = new SourcesEntity();
			sourcesEntity.setKeyword(key);
		}

		sourcesEntity.setName(name);
		sourcesEntity.setSimpleName(simpleName);
		sourcesEntity.setAbName(abName);
		sourcesEntity.setUrl(url);
		sourcesEntity.setVersionDef(versionDef);
		sourcesEntity.setVersionDate(versionDate);


		allDaoUtil.getSourceDao().save(sourcesEntity);
	}
}