package xie.playdatacollect.influxdb.data;

import org.influxdb.dto.Point;
import xie.common.utils.utils.XConvertUtils;
import xie.playdatacollect.common.data.CollectedData;
import xie.playdatacollect.common.data.CollectedDataImpl;
import xie.playdatacollect.common.utils.PlayDataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CollectedDataInflux extends CollectedDataImpl {

	List<String> tagNameList = new ArrayList<>();

	public CollectedDataInflux() {
		initTagName();
	}

	@Override
	public void cloneTo(CollectedData collectedData) {
		super.cloneTo(collectedData);
	}

	/**
	 * 根据CollectedData克隆一个新的Influx用数据，默认Tag为site和name
	 *
	 * @param collectedData
	 * @return
	 */
	public static CollectedDataInflux create(CollectedData collectedData) {
		CollectedDataInflux collectedDataInflux = new CollectedDataInflux();
		collectedData.cloneTo(collectedDataInflux);
		return collectedDataInflux;
	}

	private void initTagName() {
		tagNameList.add("site");
		tagNameList.add("name");
		tagNameList.add("type");
	}

	/**
	 * 增加一个tag，同时会添加到中英文映射中去
	 */
	public void addTag(String tagNameEn, String tagNameCN) {
		tagNameList.add(tagNameEn);
		addCNName(tagNameEn, tagNameCN);
	}

	public List<String> getTagList() {
		return tagNameList;
	}

	public Point createPoint() {
		Point.Builder builder = Point.measurement("base_data");

		Map<String, Object> allData = getAllData();
		for (String key : allData.keySet()) {
			Object val = getValue(key);
			if (val == null) {
				continue;
			}

			if (tagNameList.contains(key)) {
				builder.tag(getCNName(key), (String) val);
			} else {
				if (val instanceof Boolean) {
					builder.addField(getCNName(key), (boolean) val);
				} else if (val instanceof Number) {
					builder.addField(getCNName(key), (Number) val);
				} else {
					builder.addField(getCNName(key), val.toString());
				}
			}
		}

		builder.time(getTime(), TimeUnit.MILLISECONDS);
		Point point = builder.build();
		return point;
	}
}
