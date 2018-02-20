package xie.module.httpclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xie.module.spring.SpringUtil;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class XHttpClientConfiguration {

	@Bean
	public XPoolingHttpClientConnectionManager getXPoolingHttpClientConnectionManager(SpringUtil springUtil) throws KeyManagementException, NoSuchAlgorithmException {
		return new XPoolingHttpClientConnectionManager();
	}

	@Bean
	public XHttpClientUtils getXHttpClientUtils() throws NoSuchAlgorithmException, KeyManagementException {
		return new XHttpClientUtils(getXPoolingHttpClientConnectionManager(null));
	}
}
