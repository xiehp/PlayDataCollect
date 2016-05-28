package com.tietuku.entity.main;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;

import com.tietuku.entity.token.Token;
import com.tietuku.entity.util.PathConfig;

import xie.tietuku.httpclient.HttpClientPoolManager;

public class PostImage {

	CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(null).build();
	HttpClientPoolManager httpClientPoolManager = new HttpClientPoolManager();
	Logger logger = Logger.getLogger(this.getClass().getName());

	private String uploadUrl;
	RequestConfig requestConfig;

	public PostImage() {
		uploadUrl = PathConfig.getProperty("tie.tu.ku.post.api");

		httpClientPoolManager = new HttpClientPoolManager();
		httpClientPoolManager.putHost(uploadUrl, -1, 10);

		httpclient = HttpClients.custom().setConnectionManager(httpClientPoolManager.getPoolingHttpClientConnectionManager()).build();
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(30000)
				.setConnectTimeout(30000)
				.setSocketTimeout(30000)
				.build();
	}

	public void close() throws IOException {
		httpclient.close();
	}

	public String doUpload(File file) throws ClientProtocolException, IOException {
		String token = PathConfig.getProperty("tie.tu.ku.token");
		return doUpload(file, token);
	}

	/**
	 * 提交图片给图库
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doUpload(File file, String token) throws ClientProtocolException, IOException {
		// 贴图库数据加密请求
		HttpPost httppost = new HttpPost(uploadUrl);
		RequestConfig requestConfig = RequestConfig.copy(this.requestConfig).build();
		httppost.setConfig(requestConfig);

		FileBody bin = new FileBody(file);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create(); // 关键
		multipartEntityBuilder.addPart("file", bin);
		multipartEntityBuilder.addPart("Token", new StringBody(token, ContentType.APPLICATION_FORM_URLENCODED));

		httppost.setEntity(multipartEntityBuilder.build());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

		PostImage postImage = new PostImage();
		postImage.test1();
		postImage.test2();
	}

	private void test1() throws ClientProtocolException, IOException {
		String token = Token.createToken(new Date().getTime() + 3600, 1340, "{\"height\":\"h\",\"width\":\"w\",\"s_url\":\"url\"}");
		token = PathConfig.getProperty("tie.tu.ku.token");
		PostImage postImage = new PostImage();
		String result = postImage.doUpload(new File("D:/work/temp/bbb2/300013.jpg"), token);
		System.out.println(result);
		result = postImage.doUpload(new File("D:/work/temp/bbb2/300013.jpg"), token);
		System.out.println(result);
	}

	private void test2() throws ClientProtocolException, IOException {

		String[] urls = new String[] { "http://www.baidu.com", "http://www.sina.com", "http://www.yahoo.com", "http://www.acgimage.com", "http://www.163.com", "http://www.cdna.com" };
		for (int i = 0; i < 100; i++) {
			HttpPost httppost = new HttpPost(urls[new Random().nextInt(urls.length)]);
			RequestConfig requestConfig = RequestConfig.copy(this.requestConfig).build();
			httppost.setConfig(requestConfig);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity).substring(0, 100));
			PoolingHttpClientConnectionManager ma = httpClientPoolManager.getPoolingHttpClientConnectionManager();
			PoolStats poolStats = ma.getTotalStats();
			System.out.println(poolStats);

			System.out.println();
		}
	}

}
