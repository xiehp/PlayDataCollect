package xie.module.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class XHttpClientProperties {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 百度翻译key */
	@Value("${xie.http.proxy.host}")
	private String httpProxyHost;

	/** 百度翻译key */
	@Value("${xie.http.proxy.port}")
	private String httpProxyPort;

	public String getHttpProxyHost() {
		return httpProxyHost;
	}

	public void setHttpProxyHost(String httpProxyHost) {
		this.httpProxyHost = httpProxyHost;
	}

	public String getHttpProxyPort() {
		return httpProxyPort;
	}

	public void setHttpProxyPort(String httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

}
