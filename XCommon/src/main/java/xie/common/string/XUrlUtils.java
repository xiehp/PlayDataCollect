package xie.common.string;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class XUrlUtils {

	public static void encodeParams(Map<String, String> param) {

	}

	/**
	 * 获得url的文件名 <br>
	 * http://xxx.com/aaa/bbb.txt -> bbb.txt <br>
	 * http://xxx.com/aaa -> aaa <br>
	 * http://xxx.com/ -> "" <br>
	 * http://xxx.com -> "" <br>
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException 
	 */
	public static String getFileName(String url) throws MalformedURLException {
		return getFileName(new URL(url));
	}

	/**
	 * 获得url的文件名 <br>
	 * http://xxx.com/aaa/bbb.txt -> bbb.txt <br>
	 * http://xxx.com/aaa -> aaa <br>
	 * http://xxx.com/ -> "" <br>
	 * http://xxx.com -> "" <br>
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileName(URL url) {
		String fileName = null;
		String path = url.getPath();
		if (path != null && path.length() > 0) {
			int index = path.lastIndexOf("/");
			if (index > -1) {
				// 有斜杠，则截取斜杠后的
				fileName = path.substring(index + 1);
			} else {
				fileName = path;
			}
		} else {
			fileName = path;
		}

		return fileName;
	}

	public static void main(String[] args) throws MalformedURLException {
		System.out.println(getFileName(new URL("http://xxx.com/aaa/bbb.txt")));
		System.out.println(getFileName(new URL("http://xxx.com/aaa/bbb")));
		System.out.println(getFileName(new URL("http://xxx.com/aaa")));
		System.out.println(getFileName(new URL("http://xxx.com/")));
		System.out.println(getFileName(new URL("http://xxx.com")));
	}
}
