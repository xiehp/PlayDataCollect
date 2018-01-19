package xie.module.httpclient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import xie.common.trust.XNoCheckX509HostnameVerifier;
import xie.common.trust.XNoCheckX509TrustManager;

//@Component
public class XPoolingHttpClientConnectionManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private PoolingHttpClientConnectionManager httpClientPoolManager;

	private RequestConfig requestConfig;
	private HttpClient httpClient;

	public XPoolingHttpClientConnectionManager() throws NoSuchAlgorithmException, KeyManagementException {
		logger.info("初始化httpClient");
		// 首先设置全局的标准cookie策略
		requestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setConnectionRequestTimeout(120000)
				.setConnectTimeout(120000)
				.setSocketTimeout(120000)
				.setProxy(getProxyHost())
				.build();

		// 创建ssl工厂
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { new XNoCheckX509TrustManager() }, null);
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new XNoCheckX509HostnameVerifier());

		// 创建http和https的注册类
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", sslConnectionSocketFactory)
				.build();

		// 根据注册类创建连接池管理
		httpClientPoolManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		// 从连接池管理创建HttpClient
		httpClient = HttpClients
				.custom()
				.setConnectionManager(httpClientPoolManager)
				.setDefaultRequestConfig(requestConfig)
				.build();
		logger.info("创建了httpClient：{}", httpClient);
	}

	private HttpHost getProxyHost() {
		{
			// 首先查看java参数是否有设定
			String proxyHostStr = System.getProperty("http.proxyHost");
			String proxyPortStr = System.getProperty("http.proxyPort");
			proxyPortStr = proxyPortStr == null ? "80" : proxyPortStr;
			if (proxyHostStr != null) {
				HttpHost proxyHost = new HttpHost(proxyHostStr, Integer.valueOf(proxyPortStr));
				logger.info("发现java参数中代理设置：{}, {}", proxyHostStr, proxyPortStr);
				return proxyHost;
			}
		}

		{
			// 查看系统是否配置 （linux）
			String http_proxy = System.getenv("http_proxy");
			if (http_proxy != null) {
				String[] proxyArr = http_proxy.split(":");
				String hostname = proxyArr[0];
				String port = proxyArr.length > 1 ? proxyArr[1] : "80";
				HttpHost proxyHost = new HttpHost(hostname, Integer.valueOf(port));
				logger.info("发现linux系统参数中代理设置：{}, {}", hostname, port);
				return proxyHost;
			}
		}

		return null;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}
}
