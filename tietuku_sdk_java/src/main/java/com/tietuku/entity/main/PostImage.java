package com.tietuku.entity.main;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tietuku.entity.token.Token;
import com.tietuku.entity.util.PathConfig;
import com.tietuku.entity.vo.TietukuUploadResponse;

import xie.common.date.XTimeUtils;
import xie.common.json.XJsonUtil;
import xie.module.httpclient.HttpClientPoolManager;

public class PostImage {

	CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(null).build();
	HttpClientPoolManager httpClientPoolManager = new HttpClientPoolManager();
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private String uploadUrl;
	RequestConfig requestConfig;

	public PostImage() {
		uploadUrl = PathConfig.getProperty("tie.tu.ku.post.api");

		httpClientPoolManager = new HttpClientPoolManager();
		httpClientPoolManager.putHost(uploadUrl, -1, 10);

		httpclient = HttpClients.custom().setConnectionManager(httpClientPoolManager.getPoolingHttpClientConnectionManager()).build();
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(120000)
				.setConnectTimeout(120000)
				.setSocketTimeout(120000)
				.build();
	}

	public void close() throws IOException {
		httpclient.close();
	}

	public String doUpload(File file) throws ClientProtocolException, IOException {
		String token = PathConfig.getProperty("tie.tu.ku.token");
		return doUpload(file, token);
	}

	public TietukuUploadResponse uploadToTietuku(File file, String tietukuToken) {
		String responseStr = null;
		TietukuUploadResponse responseUpload = null;
		String tietukuUrl = null;
		try {
			responseStr = doUpload(file, tietukuToken);
		} catch (Exception e) {
			logger.error("贴图库上传失败，", e);
		}
		logger.info("贴图库上传responseStr:" + responseStr);
		if (responseStr != null) {
			responseUpload = XJsonUtil.fromJsonString(responseStr, TietukuUploadResponse.class);
			tietukuUrl = responseUpload.getLinkurl();
		}
		logger.info("tietukuUrl:" + tietukuUrl);
		if (tietukuUrl == null) {
			if (responseUpload != null) {
				logger.error("贴图库上传失败，返回值：{},{}", responseUpload.getCode(), responseUpload.getInfo());
				try {
					if ("4019".equals(responseUpload.getCode())) {
						long sleepTime = XTimeUtils.getNeedTimeNextHour();
						sleepTime += 300 * 1000;
						logger.error("暂停" + (sleepTime / 1000) + "秒");
						Thread.sleep(sleepTime);
						logger.error("暂停结束");
					}
				} catch (InterruptedException e) {
					logger.error("InterruptedException，", e);
				}
			}

			logger.error("贴图库上传失败，等待2分钟再次上传");
			try {
				Thread.sleep(60 * 2000);
				responseStr = doUpload(file, tietukuToken);
			} catch (Exception e) {
				logger.error("贴图库再次上传失败，", e);
			}
			logger.info("贴图库再次上传responseStr:" + responseStr);
			if (responseStr != null) {
				responseUpload = XJsonUtil.fromJsonString(responseStr, TietukuUploadResponse.class);
				tietukuUrl = responseUpload.getLinkurl();
			}
			if (tietukuUrl == null) {
				if (responseUpload != null) {
					logger.error("贴图库上传失败，返回值：{},{}", responseUpload.getCode(), responseUpload.getInfo());
					throw new RuntimeException("贴图库上传失败，返回值：" + responseUpload.getCode() + "," + responseUpload.getInfo());
				} else {
					throw new RuntimeException("贴图库上传失败");
				}
			}
		}
		return responseUpload;
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
		// HttpPost httppost = new HttpPost("http://192.168.4.50:9400/officialsite/attachmentUploader/aaaaaaaaa");
		RequestConfig requestConfig = RequestConfig.copy(this.requestConfig).build();
		httppost.setConfig(requestConfig);

		FileBody bin = new FileBody(file);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(Consts.UTF_8);
		multipartEntityBuilder.addPart("file", bin);
		multipartEntityBuilder.addPart("Token", new StringBody(token, ContentType.APPLICATION_FORM_URLENCODED));

		BasicHttpContext httpContext = new BasicHttpContext();
		httppost.setEntity(multipartEntityBuilder.build());
		HttpResponse response = httpclient.execute(httppost, httpContext);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

		PostImage postImage = new PostImage();
		postImage.test1();
		// postImage.test2();
	}

	private void test1() throws ClientProtocolException, IOException {
		String token = Token.createToken(new Date().getTime() + 3600, 1340, "{\"height\":\"h\",\"width\":\"w\",\"s_url\":\"url\"}");
		token = PathConfig.getProperty("tie.tu.ku.token");
		PostImage postImage = new PostImage();
		String result = postImage.doUpload(new File("D:/work/temp/心灵想要大声呼喊。  第01集15000.jpg"), token);
		System.out.println(result);
		// result = postImage.doUpload(new File("D:/work/temp/bbb2/300013.jpg"), token);
		System.out.println(result);
	}

	private void test2() throws ClientProtocolException, IOException {

		String[] urls = new String[] { "http://www.acgwiki.pub", "http://www.acgimage.com", "http://www.baidu.com", "http://www.sina.com", "http://www.yahoo.com", "http://www.acgimage.com", "http://www.163.com", "http://www.cdna.com" };
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
