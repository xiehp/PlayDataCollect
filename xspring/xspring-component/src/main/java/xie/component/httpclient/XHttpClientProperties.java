package xie.component.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class XHttpClientProperties {
	@Value("${xie.httpclient.proxy.httpProxyHost:}")
	private String httpProxyHost;

	@Value("${xie.httpclient.proxy.httpProxyPort:}")
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
