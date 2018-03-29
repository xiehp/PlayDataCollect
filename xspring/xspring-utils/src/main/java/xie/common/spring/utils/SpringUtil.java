package xie.common.spring.utils;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import xie.common.utils.java.JVMResource;
import xie.common.utils.log.XLog;

import javax.annotation.Resource;
import java.util.*;

/**
 * Spring组件工具类
 * <p>
 * <pre>
 * Pattern : Value Object
 * Thread Safe : No
 *
 * Change History
 *
 * Name                 Date                    Description
 * -------              -------                 -----------------
 * 020191              2014-3-31            Create the class
 *
 * </pre>
 *
 * @author 020191
 * @version 1.0
 */
@Component
public class SpringUtil implements BeanFactoryAware {
	public final static String WEB_TYPE = "W";
	public final static String BEAN_TYPE = "B";

	public final static String BEAN_CONTENT_TYPE = "B";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = XLog.getLog(SpringUtil.class);

//	/** The locator. */
//	private static volatile BeanFactoryLocator locator;
//
//	/** The bfr. */
//	private static BeanFactoryReference bfr;

	/** The factory. */
	private static ApplicationContext ctx;

	/** The factory. */
	private static BeanFactory factory;

	@Resource
	private ConfigurableEnvironment environment;
	@Resource
	private InfoProperties infoProperties;

	/**
	 * 根据对象名获得对象
	 *
	 * @param beanRef 对象名
	 * @return Object对象
	 */
	@SuppressWarnings("unchecked")
	public static <X> X getBean(final String beanRef) {
		return (X) getBeanFactory().getBean(beanRef);
	}

	/**
	 * 根据对象类型获得对象
	 *
	 * @param clazz 对象类型
	 * @return <X> X 对象
	 */
	public static <X> X getBean(final Class<X> clazz) {
		return getBeanFactory().getBean(clazz);
	}

	public static String getProperty(String key) {
		return ctx.getEnvironment().getProperty(key);
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}

	public static List<String> getNowProfilesList() {
		getBeanFactory();

		List<String> nowProfilesList = new ArrayList<>();
		String[] activeProfiles = SpringUtil.getCtx().getEnvironment().getActiveProfiles();
		if (activeProfiles != null && activeProfiles.length > 0) {
			for (String profile : activeProfiles) {
				nowProfilesList.add(profile);
			}
			return nowProfilesList;
		}

		String[] defaultProfiles = SpringUtil.getCtx().getEnvironment().getDefaultProfiles();
		if (defaultProfiles != null && defaultProfiles.length > 0) {
			for (String profile : defaultProfiles) {
				nowProfilesList.add(profile);
			}
			return nowProfilesList;
		}

		return nowProfilesList;
	}

	public static void printNowProfilesList() {
		List<String> list = getNowProfilesList();

		String[] defaultProfiles = SpringUtil.getCtx().getEnvironment().getDefaultProfiles();
		LOGGER.info("当前默认的profile：" + defaultProfiles.length + "个");
		for (String value : defaultProfiles) {
			LOGGER.info(value);
		}

		String[] activeProfiles = SpringUtil.getCtx().getEnvironment().getActiveProfiles();
		LOGGER.info("当前激活的profile：" + activeProfiles.length + "个");
		for (String value : activeProfiles) {
			LOGGER.info(value);
		}

		LOGGER.info("当前实际profile：");
		for (String str : list) {
			LOGGER.info(str);
		}
	}

	public void printNowProfilesListByEnvironment() {
		printNowProfilesListByEnvironment(environment, infoProperties);
	}

	public static void printNowProfilesListByEnvironment(ConfigurableEnvironment environment) {
		printNowProfilesListByEnvironment(environment, null);
	}

	public static void printNowProfilesListByEnvironment(ConfigurableEnvironment environment, InfoProperties infoProperties) {

		System.setProperty("druid.log.stmt", "false");
		System.setProperty("druid.log.stmt.executableSql", "true");


		LOGGER.info("-----------------------------------------------");

		String[] defaultProfiles = environment.getDefaultProfiles();
		String[] activeProfiles = environment.getActiveProfiles();
		LOGGER.info("--------jvm信息--------------------------------");
		new JVMResource().printAllSummary();

		LOGGER.info("");
		LOGGER.info("--------spring信息-----------------------------");
		{
			LOGGER.info("当前默认的profile                    :{}个，{}", defaultProfiles.length, Arrays.asList(defaultProfiles));
			LOGGER.info("当前激活的profile                    :{}个，{}", activeProfiles.length, Arrays.asList(activeProfiles));
		}
		if (infoProperties == null) {
			LOGGER.info("info.project.groupId                :" + environment.getProperty("info.project.groupId"));
			LOGGER.info("info.project.artifactId             :" + environment.getProperty("info.project.artifactId"));
			LOGGER.info("info.project.version                :" + environment.getProperty("info.project.version"));
			LOGGER.info("info.project.parent.version         :" + environment.getProperty("info.project.parent.version"));
			LOGGER.info("info.project.compile.version        :" + environment.getProperty("info.project.compile.version"));
			LOGGER.info("info.project.compile.encoding       :" + environment.getProperty("info.project.compile.encoding"));
			LOGGER.info("info.project.spring.version         :" + environment.getProperty("info.project.spring.version"));
			LOGGER.info("info.project.spring.io.version      :" + environment.getProperty("info.project.spring.io.version"));
			LOGGER.info("info.project.spring.boot.version    :" + environment.getProperty("info.project.spring.boot.version"));
		} else {
			LOGGER.info("info                                :{}", infoProperties.getInfo());
			for (String key : infoProperties.getInfo().keySet()) {
				LOGGER.info("{} : {}", key, infoProperties.getInfo().get(key));
			}
		}
		{
			LOGGER.info("environment.propertySources         :");
			// LOGGER.info("environment.propertySources         :" + environment.getPropertySources());
			Iterator<PropertySource<?>> it = environment.getPropertySources().iterator();
			while (it.hasNext()) {
				PropertySource propertySource = it.next();
				LOGGER.info(propertySource.getName());
//					LOGGER.info("{} : {}", propertySource.getName(), propertySource.getSource());
			}
		}
		{
			LOGGER.info("---------db信息----------");
			LOGGER.info("spring.datasource.url               :" + environment.getProperty("spring.datasource.url"));
			LOGGER.info("spring.datasource.driver-class-name :" + environment.getProperty("spring.datasource.driver-class-name"));
			LOGGER.info("spring.datasource.driverClassName   :" + environment.getProperty("spring.datasource.driverClassName"));

			LOGGER.info("-------------------------");
			LOGGER.info("-------------------------");
		}
	}

	/**
	 * 初始化Factory
	 */
	private static BeanFactory getBeanFactory() {
		if (factory == null) {
			synchronized (SpringUtil.class) {
				if (factory == null) {
					// if (BEAN_TYPE.equalsIgnoreCase(BEAN_CONTENT_TYPE)) {
					// try {
					// if (null == locator) {
					// locator = ContextSingletonBeanFactoryLocator.getInstance("classpath*:beanRefContext.xml");
					// }
					// if (null == bfr) {
					// bfr = locator.useBeanFactory("beanfactory");
					// }
					// if (null == factory) {
					// factory = bfr.getFactory();
					// }
					// } catch (Exception e) {
					// LOGGER.error(e.getMessage(), e);
					// }
					// } else {
					// // factory = SpringWebUtil.getBeanFactory();
					// }

					LOGGER.warn("未发现自动设置bean，开始手动加载applicationContext.xml");

					ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
					factory = ((ClassPathXmlApplicationContext) ctx).getBeanFactory();
					LOGGER.info("手动加载结束，xml：" + factory + ", ctx.getApplicationName():" + ctx.getApplicationName() + ", ctx.getDisplayName():" + ctx.getDisplayName());

					printNowProfilesList();
				}
			}
		}

		return factory;
	}

	@Override
	public void setBeanFactory(final BeanFactory factory) throws BeansException {
		if (SpringUtil.factory == null) {
			SpringUtil.factory = factory;

//			LOGGER.info("由系统自动设置BeanFactory：" + factory);
			LOGGER.info("由系统自动设置BeanFactory");
		} else {
			LOGGER.warn("输入的BeanFactory：" + factory.getClass());
			LOGGER.warn("已存在BeanFactory：" + SpringUtil.factory.getClass());
			LOGGER.warn("不进行BeanFactory处理");
		}

		// printNowProfilesListByEnvironment();
	}
}
