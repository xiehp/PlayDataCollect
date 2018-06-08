package xie.framework.web.utils;

import xie.common.utils.date.XDateUtil;
import xie.common.utils.string.StringPool;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class UploadUtil {

	public final static String DEFAULT = "default";

	public static String[] getCreatePathWithSuffix(String realName, String sign, String fileUploadPath) throws Exception {
		String type = realName.substring(realName.lastIndexOf(".") + 1);
		String suffix = "";
		if (!type.isEmpty()) {
			suffix = StringPool.PERIOD + type;
		}
		String path = fileUploadPath;

		String name = UUID.randomUUID().toString() + "-" + (int) (Math.random() * 10000) + suffix;

		String dateStr = XDateUtil.convertToString(new Date(), XDateUtil.YMD1);

		String relativePath = dateStr + StringPool.FORWARD_SLASH + (sign == null ? DEFAULT : sign) + StringPool.FORWARD_SLASH + name;

		path = path + StringPool.FORWARD_SLASH + relativePath;

		File file = new File((new File(path)).getParent());
		file.mkdirs();

		return new String[]{path, relativePath, name};
	}

}
