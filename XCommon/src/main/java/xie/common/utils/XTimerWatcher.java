package xie.common.utils;

import java.util.Date;
import java.util.List;

public class XTimerWatcher {
	private Date startDate;
	private List<Date> recordDateList;
	private Date endDate;

	public XTimerWatcher() {
		startDate = new Date();
	}
}
