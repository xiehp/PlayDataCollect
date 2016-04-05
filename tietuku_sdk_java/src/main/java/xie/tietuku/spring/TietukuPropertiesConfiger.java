package xie.tietuku.spring;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class TietukuPropertiesConfiger  {
//
//	private static Map<String, String> proMap = new HashMap<String, String>();
//
//	@Override
//	protected void convertProperties(Properties props) {
//		System.out.println("convertProperties 1");
//		super.convertProperties(props);
//
//		System.out.println(props);
//		for (Object key : props.keySet()) {
//			String keyStr = key.toString();
//			String value = props.getProperty(keyStr);
//			proMap.put(keyStr, value);
//
//			System.out.println(keyStr + value);
//		}
//		System.out.println("convertProperties 2");
//	}
//
////	@Override
////	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
////		System.out.println("processProperties 1");
////		System.out.println(props);
////		for (Object key : props.keySet()) {
////			String keyStr = key.toString();
////			String value = props.getProperty(keyStr);
////			proMap.put(keyStr, value);
////
////			System.out.println(keyStr + value);
////		}
////		super.processProperties(beanFactoryToProcess, props);
////
////		System.out.println("processProperties 2");
////	}
//
//	@Override
//	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		System.out.println("postProcessBeanFactory 1");
//		// TODO Auto-generated method stub
//		super.postProcessBeanFactory(beanFactory);
//		System.out.println("postProcessBeanFactory 2");
//	}
//
//	private String nowProfile;
//
//	public String getNowProfile() {
//		return proMap.get("tomcat.deploy.active.spring.profile");
//	}
//
//	public void setNowProfile(String nowProfile) {
//		this.nowProfile = nowProfile;
//	}
}
