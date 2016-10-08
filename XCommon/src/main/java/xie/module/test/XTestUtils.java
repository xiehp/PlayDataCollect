package xie.module.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

}
