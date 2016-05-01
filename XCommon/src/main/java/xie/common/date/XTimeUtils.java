package xie.common.date;

import java.text.ParseException;
import java.util.Date;

public class XTimeUtils {

	public static long timeDif;

	static {
		try {
			timeDif = DateUtil.convertFromString("00:00:00.00", "HH:mm:ss.SS").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static long parseFromTimeStr(String timeStr, String format) throws ParseException {
		Date date = DateUtil.convertFromString(timeStr, format);
		return date.getTime() - timeDif;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(XTimeUtils.parseFromTimeStr("11:11:11.11", "HH:mm:ss.SS"));
		System.out.println(DateUtil.convertFromString("00:00:00.111", "HH:mm:ss.SSS").getTime());
		System.out.println(DateUtil.convertFromString("00:00:00.11", "H:m:s.S").getTime());
		System.out.println(DateUtil.convertFromString("00:00:10.11", "HH:mm:ss.SS").getTime());
		System.out.println(DateUtil.convertFromString("00:00:10.111", "HH:mm:ss.SSS").getTime());
		System.out.println(DateUtil.convertFromString("01:00:10.11", "H:m:s.S").getTime());
		System.out.println(XTimeUtils.parseFromTimeStr("00:00:10.11", "HH:mm:ss.SS"));
		System.out.println(XTimeUtils.parseFromTimeStr("00:00:10.111", "HH:mm:ss.SSS"));
		System.out.println(XTimeUtils.parseFromTimeStr("00:01:10.111", "HH:mm:ss.SSS"));
		System.out.println(XTimeUtils.parseFromTimeStr("01:00:10.11", "H:m:s.S"));
		;
	}
}
