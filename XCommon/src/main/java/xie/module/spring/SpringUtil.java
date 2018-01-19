package xie.module.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Spring组件工具类
 *
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

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SpringUtil.class);

//	/** The locator. */
//	private static volatile BeanFactoryLocator locator;
//
//	/** The bfr. */
//	private static BeanFactoryReference bfr;

	/** The factory. */
	private static ApplicationContext ctx;

	/** The factory. */
	private static BeanFactory factory;

	/**
	 * 
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
	 * 
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

		List<String> nowProfilesList = new ArrayList<String>();
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
		LOG.info("当前默认的profile：" + defaultProfiles.length + "个");
		for (String value : defaultProfiles) {
			LOG.info(value);
		}

		String[] activeProfiles = SpringUtil.getCtx().getEnvironment().getActiveProfiles();
		LOG.info("当前激活的profile：" + activeProfiles.length + "个");
		for (String value : activeProfiles) {
			LOG.info(value);
		}

		LOG.info("当前实际profile：");
		for (String str : list) {
			LOG.info(str);
		}
	}

	/**
	 * 
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
					// LOG.error(e.getMessage(), e);
					// }
					// } else {
					// // factory = SpringWebUtil.getBeanFactory();
					// }

					LOG.warn("未发现自动设置bean，开始手动加载applicationContext.xml");

					ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
					factory = ((ClassPathXmlApplicationContext) ctx).getBeanFactory();
					LOG.info("手动加载结束，xml：" + factory + ", ctx.getApplicationName():" + ctx.getApplicationName() + ", ctx.getDisplayName():" + ctx.getDisplayName());

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

			LOG.info("由系统自动设置BeanFactory：" + factory);
		} else {
			LOG.warn("输入的BeanFactory：" + factory.getClass());
			LOG.warn("已存在BeanFactory：" + SpringUtil.factory.getClass());
			LOG.warn("不进行BeanFactory处理");
		}
	}
}
