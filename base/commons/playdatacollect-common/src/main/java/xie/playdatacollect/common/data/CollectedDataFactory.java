package xie.playdatacollect.common.data;

public class CollectedDataFactory {

	long time;

	public CollectedDataFactory(long time) {
		this.time = time;
	}

	public CollectedData create() {
		CollectedData collectedData = new CollectedDataImpl();
		collectedData.setTime(time);
		return collectedData;
	}
}
