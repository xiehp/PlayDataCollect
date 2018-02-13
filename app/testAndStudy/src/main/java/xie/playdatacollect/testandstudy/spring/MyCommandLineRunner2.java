package xie.playdatacollect.testandstudy.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(102)
public class MyCommandLineRunner2 implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(MyCommandLineRunner2.class);

	@Override
	public void run(String... args) {
		log.info("MyCommandLineRunner2 start");
	}

}


