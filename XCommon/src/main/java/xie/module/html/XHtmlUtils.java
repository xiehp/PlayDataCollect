package xie.module.html;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import xie.common.utils.XRegularUtils;
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

	public void getXXX(Document doc, String cssSelectQuery) {
		Elements elements = doc.select("a[href*='KamigamiMabors-Saenai-Heroine-no-Sodatekata-Flat-05-1080p-x265-Ma10p-AAC.mkv.torrent']");
	}

	public void run() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {

		Document doc = getDocumentByUrl("https://sub.kamigami.org/78495.html");
		// System.out.println(doc.html());

		Elements elements = doc.select("#wp-admin-bar-top-secondary");

		// System.out.println(elements.size());
		// System.out.println(elements.html());
		// System.out.println(elements.get(0).html());

		elements = doc.select("a[href*=torrent]");

		// System.out.println(elements.size());
		// System.out.println(elements.html());
		// System.out.println(elements.get(0).html());

		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setDoNotTrackEnabled(false);
		HtmlPage page = webClient.getPage("https://sub.kamigami.org/78495.html");
		//HtmlPage page = webClient.getPage("https://www.acgimage.com/search?name=&keyword=%E4%BD%A0%E5%A5%BD");
		
		Thread.sleep(10000);
		System.out.println(page.asText());
		String xmlStr = page.asXml();
		System.out.println(xmlStr);

		DomElement domElement = page.getElementById("ep-1080-0");
		System.out.println(domElement == null ? "null" : domElement.asXml());

		try {
			DomElement domElement2 = page.getHtmlElementById("ep-1080-0");
			System.out.println(domElement2 == null ? "null" : domElement.asXml());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(page.getTextContent());

		List<?> list = page.getByXPath("//a");
		System.out.println(list.size());
		list.forEach(ob -> {
			System.out.println(ob);
		});
		
		
		List<String>  listTorrent = XRegularUtils.find(xmlStr, "http.*.torrent");
		listTorrent.forEach(str->{
			System.out.println(str);
		});
	}

	public static void main(String[] args) throws IOException, FailingHttpStatusCodeException, InterruptedException {

		System.setProperty("spring.profiles.default", "development");
		XHtmlUtils xHtmlUtils = SpringUtil.getBean(XHtmlUtils.class);
		xHtmlUtils.run();
	}
}
