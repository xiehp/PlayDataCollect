package xie.playdatacollect.base.db.entity;

import org.dozer.DozerBeanMapper;
import org.hibernate.annotations.GenericGenerator;
import xie.common.collect.XCollectUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public abstract class IdEntity implements Serializable {

	private static final long serialVersionUID = 4560753499836400652L;

	protected static final DozerBeanMapper dozerMapper = new DozerBeanMapper();
	static {
	}

	public static final String COLUMN_ID = "id";

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	//Column(length = 36)
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		try {
			return toMap().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toString();
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		dozerMapper.map(this, map);
		return map;
	}

	public Map<String, Object> toMapWithOutNull() {
		Map<String, Object> map = toMap();
		XCollectUtils.removeNull(map);
		return map;
	}

	public Map<String, Object> toMapWithOutBase() {
		Map<String, Object> map = toMap();
		// map.remove(COLUMN_ID);
		List<String> list = Arrays.asList(COLUMN_ID);
		XCollectUtils.removeKey(map, list, true);

		return map;
	}

	public Map<String, Object> toMapWithOutNullAndBase() {
		Map<String, Object> map = toMapWithOutBase();
		XCollectUtils.removeNull(map);
		return map;
	}
}
