package xie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootStudyApplication {

	public static void main(String[] args) {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");
		ConfigurableApplicationContext run = SpringApplication.run(SpringBootStudyApplication.class, args);
		String property = run.getEnvironment().getProperty("ds.password");
		System.out.println("property:" + property);
	}
}
