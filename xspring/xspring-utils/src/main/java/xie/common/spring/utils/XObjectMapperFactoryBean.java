package xie.common.spring.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class XObjectMapperFactoryBean implements FactoryBean<ObjectMapper>, BeanClassLoaderAware, InitializingBean {

	private static ObjectMapper objectMapper;

	public XObjectMapperFactoryBean() {
	}

	@Override
	public void afterPropertiesSet() {

	}

	@Override
	public void setBeanClassLoader(ClassLoader arg0) {
	}

	@Override
	public synchronized ObjectMapper getObject() {
		if (objectMapper == null) {
			// 目标类中找不到json字符串中属性时直接忽略
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			// 应该通过TimeZone.setDefault设置整个java时区
			// objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		}

		return objectMapper;
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
