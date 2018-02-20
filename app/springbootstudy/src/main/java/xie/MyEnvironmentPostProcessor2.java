package xie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class MyEnvironmentPostProcessor2 implements EnvironmentPostProcessor {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		logger.info("MyEnvironmentPostProcessor, {}", this);
	}

}