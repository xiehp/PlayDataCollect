package xie.module.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import xie.common.utils.base.XEnum;
import xie.module.language.translate.baidu.XELangBaidu;

public class XTestUtils {

	public static <X> X getBean(Class<X> clazz, Class<?>... otherClazzs) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(clazz);
		for (Class<?> c : otherClazzs) {
			applicationContext.register(c);
		}
		applicationContext.refresh();

		X x = applicationContext.getBean(clazz);
		applicationContext.close();

		return x;
	}


	public static void main(String[] args) throws InterruptedException {
		System.out.println(XEnum.parseValue("ara", XELangBaidu.class));
		System.out.println(XEnum.parseValue("ara", XELangBaidu.class));
		System.out.println(XEnum.parseValue("ara", XELangBaidu.class));
		System.out.println(XEnum.parseValue("auto", XELangBaidu.class));
		System.out.println(XEnum.parseValue("auto", XELangBaidu.class));
		System.out.println(XEnum.parseValue("auto", XELangBaidu.class));
		System.out.println(XEnum.parseValue("auto", XELangBaidu.class));
		//System.out.println(XEnum.parseValue("1"));

	}
}
