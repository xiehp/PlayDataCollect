package xie.playdatacollect.spider.webmagic.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * info:
 * Created by shang on 16/7/8.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
//@EnableScheduling
public class SprayApplication {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(SprayApplication.class);
//	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SprayApplication.class, args);
	}
}
