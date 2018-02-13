package xie.playdatacollect.base.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot 启动监听类
 *
 * @author liaokailin
 * @version $Id: MyApplicationStartedEventListener.java, v 0.1 2015年9月2日 下午11:06:04 liaokailin Exp $
 */
public abstract class XApplicationListener<E extends ApplicationEvent> implements ApplicationListener<E> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
}