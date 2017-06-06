package xie.common.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XPrintUtils {

	private static final Logger log = LoggerFactory.getLogger(XPrintUtils.class);

	public static void printList(List<?> list) {
		if (list == null) {
			return;
		}

		list.forEach(obj -> {
			System.out.println(obj);
		});
	}

	public static void infoList(List<?> list) {
		infoList(log, list);
	}

	public static void infoList(Logger log, List<?> list) {
		if (list == null) {
			return;
		}

		list.forEach(obj -> {
			if (obj != null) {
				log.info(obj.toString());
			} else {
				log.info(null);
			}
		});
	}

	/**
	 * 
	 * @param log
	 * @param list
	 * @param notPrintStr 含有改文字时不打印
	 */
	public static void infoList(Logger log, List<?> list, String notPrintStr) {
		if (list == null) {
			return;
		}

		list.forEach(obj -> {
			if (obj != null) {
				String str = obj.toString();
				if (str != null && notPrintStr != null && str.contains(notPrintStr)) {
					// 不打印
				} else {
					log.info(obj.toString());
				}
			} else {
				log.info(null);
			}
		});
	}
}
