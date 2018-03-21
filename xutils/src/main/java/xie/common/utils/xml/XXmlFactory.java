package xie.common.utils.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import xie.common.utils.exception.XRuntimeException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XXmlFactory {

	public static Document readXml(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile); // 使用dom解析xml文件
		return doc;
	}

	public static Document createNewXml() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			return doc;
		} catch (ParserConfigurationException e) {
			throw new XRuntimeException(e);
		}
	}

	public static void saveXml(Document document, File file) throws TransformerException {
		Source xmlSource = new DOMSource(document);
		// 使用工厂模式，实例化Transformer
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();

		// 参数
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 增加换行缩进，但此时缩进默认为0
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");// 设置缩进为2

		// 实例化结果树，结果树指转换后（对于stremResult包含了结果的输出流）结果的持有者
		Result result = new StreamResult(file);
		// Transformer能够将源树转换成结果树
		transformer.transform(xmlSource, result);
	}
}
