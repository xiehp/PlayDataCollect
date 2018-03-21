package xie.module.download;

import org.apache.commons.io.FileUtils;
import xie.common.utils.trust.XNoCheckX509TrustManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class XDownloadUtils {

	public static void download(URL url, File toFile) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
		download(url, toFile, 10000, 100000);
	}

	public static void download(URL url, File toFile, int connectionTimeout, int readTimeout) throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, IOException {
		if (url.getProtocol().contains("https")) {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new XNoCheckX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
			HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
			httpsConn.setSSLSocketFactory(ssf);
			httpsConn.setConnectTimeout(connectionTimeout);
			httpsConn.setReadTimeout(readTimeout);
			httpsConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			FileUtils.copyInputStreamToFile(httpsConn.getInputStream(), toFile);
		} else {
			final URLConnection connection = url.openConnection();
			connection.setConnectTimeout(connectionTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			FileUtils.copyInputStreamToFile(connection.getInputStream(), toFile);
		}
	}

	public static void main(String[] args) {

	}
}
