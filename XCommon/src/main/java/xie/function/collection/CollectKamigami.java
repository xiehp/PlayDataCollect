package xie.function.collection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import xie.common.utils.XRegularUtils;

public class CollectKamigami {
	public static List<String> getTorrentUrlList(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return getTorrentUrlList(url, "http.*1080.*torrent");
	}

	public static List<String> getTorrentUrlList(String url, String findRegStr) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		// webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setDoNotTrackEnabled(false);

		HtmlPage page = webClient.getPage(url);
		String xmlStr = page.asXml();
		List<String> listTorrent = XRegularUtils.find(xmlStr, findRegStr);
		listTorrent.forEach(str -> {
			System.out.println(str);
		});

		return listTorrent;
	}

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		getTorrentUrlList("https://sub.kamigami.org/78495.html", "http.*1080.*torrent");
		getTorrentUrlList("https://sub.kamigami.org/78320.html", "http.*1080.*torrent");
		getTorrentUrlList("https://sub.kamigami.org/78332.html", "http.*1080.*torrent");

		getTorrentUrlList("https://sub.kamigami.org/78495.html", "http.*-02.*1080.*torrent");
		getTorrentUrlList("https://sub.kamigami.org/78320.html", "http.*-03.*1080.*torrent");
		getTorrentUrlList("https://sub.kamigami.org/78332.html", "http.*-04.*1080.*torrent");
	}
}
