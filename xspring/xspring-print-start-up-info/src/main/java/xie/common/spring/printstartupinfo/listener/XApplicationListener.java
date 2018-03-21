package xie.common.spring.printstartupinfo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring boot 启动监听类
 */
public abstract class XApplicationListener<E extends ApplicationEvent> implements ApplicationListener<E> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
}