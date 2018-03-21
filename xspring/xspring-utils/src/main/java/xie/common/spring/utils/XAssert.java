package xie.common.spring.utils;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

public class XAssert extends Assert {

	/**
	 * 验证是否超过最大条数
	 */
	public static void maxSize(Map<?, ?> m, int maxSize) {
		if (m == null) {
			return;
		}

		Assert.isTrue(m.size() <= maxSize, "超过" + maxSize + "条以上数据");
	}

	/**
	 * 验证是否超过最大条数
	 */
	public static void maxSize(Collection<?> c, int maxSize) {
		if (c == null) {
			return;
		}

		Assert.isTrue(c.size() <= maxSize, "超过" + maxSize + "条以上数据");
	}
}
