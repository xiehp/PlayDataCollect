package xie.module.html;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import xie.module.httpclient.XHttpClientUtils;
import xie.module.spring.SpringUtil;

@Component
public class XHtmlUtils {

	@Resource
	private XHttpClientUtils httpClientUtils;

	public Document getDocumentByUrl(String url) throws IOException {
		String html = httpClientUtils.getHtml(url);
		return getDocument(html);
	}

	public Document getDocument(String html) {
		Document doc = Jsoup.parse(html);
		return doc;
	}

	public String getHtml(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		// webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setDoNotTrackEnabled(false);

		HtmlPage page = webClient.getPage(url);
		String xmlStr = page.asXml();
		webClient.close();
		
		return xmlStr;
	}

	public void run() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {

	}

	public static void main(String[] args) throws IOException, FailingHttpStatusCodeException, InterruptedException {

		System.setProperty("spring.profiles.default", "development");
		XHtmlUtils xHtmlUtils = SpringUtil.getBean(XHtmlUtils.class);
		xHtmlUtils.run();
	}
}
