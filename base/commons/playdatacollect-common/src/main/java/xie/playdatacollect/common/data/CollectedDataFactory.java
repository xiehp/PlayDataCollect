package xie.playdatacollect.common.data;

public class CollectedDataFactory {

	long time;
	String site;
	String type;

	public CollectedDataFactory(long time) {
		this.time = time;
	}

	public CollectedDataFactory(long time, String site, String type) {
		this.time = time;
		this.site = site;
		this.type = type;
	}

	public CollectedData create() {
		return create(time, site, type);
	}

	public CollectedData create(long time, String site, String type) {
		CollectedData collectedData = new CollectedDataImpl();
		collectedData.setTime(time);
		collectedData.setSite(site);
		collectedData.setType(type);
		return collectedData;
	}
}
