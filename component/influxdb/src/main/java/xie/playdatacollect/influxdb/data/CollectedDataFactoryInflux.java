package xie.playdatacollect.influxdb.data;

import xie.playdatacollect.common.data.CollectedDataFactory;

public class CollectedDataFactoryInflux extends CollectedDataFactory {

	public CollectedDataFactoryInflux(long time) {
		super(time);
	}

	public CollectedDataFactoryInflux(long time, String site, String type) {
		super(time, site, type);
	}
}
