package xie.playdatacollect.testandstudy.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCommandLineRunner2 implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(MyCommandLineRunner2.class);

	@Override
	public void run(String... args) throws Exception {
		log.info("MyCommandLineRunner2 start");
	}

}


