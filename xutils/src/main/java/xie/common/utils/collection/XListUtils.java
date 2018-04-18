package xie.common.utils.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class XListUtils {

	public static <T> ArrayList<T> copy(List<T> srcList) {

		ArrayList<T> newList = new ArrayList<>();
		for (T o : srcList) {
			newList.add(o);
		}

		return newList;
	}

	/**
	 * 从list获得符合函数的结果
	 */
	public static <T> T getValueByKey(List<T> list, Function<T, Boolean> equalFilterFun) {
		if (list == null) {
			return null;
		}

		for (T obs : list) {
			boolean equalFlag = equalFilterFun.apply(obs);
			if (equalFlag) {
				return obs;
			}
		}

		return null;
	}
}
