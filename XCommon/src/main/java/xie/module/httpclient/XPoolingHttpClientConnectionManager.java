package xie.module.httpclient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class XPoolingHttpClientConnectionManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CloseableHttpClient httpClient;
	private PoolingHttpClientConnectionManager httpClientPoolManager;
	private RequestConfig requestConfig;

	public XPoolingHttpClientConnectionManager() throws NoSuchAlgorithmException, KeyManagementException {
		// 首先设置全局的标准cookie策略
		requestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setConnectionRequestTimeout(120000)
				.setConnectTimeout(120000)
				.setSocketTimeout(120000)
				.build();

		// 创建ssl工厂
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { new NoCheckX509TrustManager() }, null);
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoCheckX509HostnameVerifier());

		// 创建http和https的注册类
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", sslConnectionSocketFactory)
				.build();

		// 根据注册类创建连接池管理
		httpClientPoolManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		// 从连接池管理创建HttpClient
		httpClient = HttpClients.custom().setConnectionManager(httpClientPoolManager).setDefaultRequestConfig(requestConfig).build();
		logger.info("创建了httpClient：{}", httpClient);
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}
}
