package xie.component.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

@Component
public class HttpClientPoolManager {
	PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

	public HttpClientPoolManager() {
//		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
//		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
//				.register("http", plainsf)
//				.register("https", sslsf)
//				.build();
//		poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
		poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		// 将最大连接数增加到200
		poolingHttpClientConnectionManager.setMaxTotal(20);
		// 将每个路由基础的连接增加到20
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);
	}

	/**
	 * 目标主机的最大连接数
	 * 
	 * @param hostname the hostname (IP or DNS name)
	 * @param port the port number. -1 indicates the scheme default port.
	 * @param maxRouteNumber
	 */
	public void putHost(String hostname, int port, int maxRouteNumber) {
		HttpHost localhost = new HttpHost(hostname, port);
		poolingHttpClientConnectionManager.setMaxPerRoute(new HttpRoute(localhost), maxRouteNumber);
	}

	public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
		return poolingHttpClientConnectionManager;
	}
}
