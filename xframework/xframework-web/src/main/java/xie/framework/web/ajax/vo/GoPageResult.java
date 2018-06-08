package xie.framework.web.ajax.vo;

import xie.common.spring.utils.XJsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * 在Controller层使用的返回结果类
 * 
 * @version 1.0
 */
public class GoPageResult extends BaseResult<Object> {
	private static final long serialVersionUID = 3497928554698321251L;
	// js 内容
	protected String js;

	private String code;
	private String message;

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public GoPageResult setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	/*public String getTemp() {
		return temp;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}*/

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * PS：此方法不会让前台js弹出信息窗口，如需要，请使用addAlertMessage
	 * 
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> convertToObjectMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		// BeanMapper.copy(this, map);
		String jsonStr = XJsonUtil.toJsonString(this);
		map = XJsonUtil.fromJsonString(jsonStr, LinkedHashMap.class);
		return map;
	}
}
