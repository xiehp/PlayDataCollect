package xie.module.httpclient;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import xie.common.constant.XConst;

@Component
public class XHttpClientUtils {

	@Resource
	XPoolingHttpClientConnectionManager manager;

	/**
	 * 获得默认的HttpClient
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpClient getHttpClient()  {
		return manager.getHttpClient();
	}

	public String getHtml(String url, String charset) throws IOException {
		// 贴图库数据加密请求
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.copy(manager.getRequestConfig()).build();
		httppost.setConfig(requestConfig);

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(Consts.UTF_8);

		BasicHttpContext httpContext = new BasicHttpContext();
		httppost.setEntity(multipartEntityBuilder.build());
		HttpResponse response = manager.getHttpClient().execute(httppost, httpContext);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity, Charset.forName(charset));
	}

	public String getHtml(String url) throws ClientProtocolException, IOException {
		return getHtml(url, XConst.CHARSET_UTF8);
	}

	public static void main(String[] args) throws IOException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(XHttpClientUtils.class);
		applicationContext.register(XPoolingHttpClientConnectionManager.class);
		applicationContext.close();
		applicationContext.refresh();
		XHttpClientUtils xHttpClientUtils = applicationContext.getBean(XHttpClientUtils.class);

		String value = xHttpClientUtils.getHtml("http://baike.baidu.com/item/NEW%20GAME%21/18751606");
		System.out.println(value);

		value = xHttpClientUtils.getHtml("https://www.acgimage.com");
		System.out.println(value);
	}
}
