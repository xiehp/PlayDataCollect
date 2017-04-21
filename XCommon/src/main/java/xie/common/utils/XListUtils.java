package xie.common.utils;

import java.util.ArrayList;
import java.util.List;

public class XListUtils {

	public static <T> ArrayList<T> copy(List<T> srcList) {

		ArrayList<T> newList = new ArrayList<>();
		for (T o : srcList) {
			newList.add(o);
		}

		return newList;
	}
}
