package xie.module.sitemap;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import xie.common.xml.XXmlFactory;

public class XSiteMap {
	public static final String CHANGEFREQ_ALWAYS = "always";
	public static final String CHANGEFREQ_HOURLY = "hourly";
	public static final String CHANGEFREQ_DAILY = "daily";
	public static final String CHANGEFREQ_WEEKLY = "weekly";
	public static final String CHANGEFREQ_MONTHLY = "monthly";
	public static final String CHANGEFREQ_YEARLY = "yearly";
	public static final String CHANGEFREQ_NEVER = "never";

	Document document;
	Element urlset;

	public XSiteMap() {
		document = XXmlFactory.createNewXml();

		urlset = document.createElement("urlset");
		urlset.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
		urlset.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		urlset.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");

		document.appendChild(urlset);
	}

	public XSiteMap(File file) throws ParserConfigurationException, SAXException, IOException {
		document = XXmlFactory.readXml(file);
	}

	public void addUrl(String loc, String changefreq, String priority, String lastmod, String title) {
		Element urlElement = document.createElement("url");

		addUrlChild(urlElement, "loc", loc);
		addUrlChild(urlElement, "lastmod", lastmod);
		addUrlChild(urlElement, "changefreq", changefreq);
		addUrlChild(urlElement, "priority", priority);
		addUrlChild(urlElement, "title", title);

		urlset.appendChild(urlElement);
	}

	private void addUrlChild(Element urlElement, String name, String value) {
		if (value == null) {
			return;
		}

		Element element = document.createElement(name);
		element.appendChild(document.createTextNode(value));
		urlElement.appendChild(element);
	}

	public void save(File file) throws TransformerException {
		XXmlFactory.saveXml(document, file);
	}

	public static void main(String[] args) throws TransformerException {
		XSiteMap xSiteMap = new XSiteMap();
		xSiteMap.addUrl("111", "222", "333", null, "333");
		xSiteMap.addUrl("111", "222", "333", null, "333");
		xSiteMap.addUrl("111", "222", "333", null, "333");
		xSiteMap.addUrl("111", "222", "333", null, "333");

		File file = new File("aaaaSiteMap.xml");
		xSiteMap.save(file);
	}
}
