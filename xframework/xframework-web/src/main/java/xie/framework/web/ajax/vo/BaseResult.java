package xie.framework.web.ajax.vo;

import java.io.Serializable;

/**
 * 
 * 在Controller层使用的返回结果类
 * 
 * @version 1.0
 */
public class BaseResult<T extends Object> implements Serializable {
	private static final long serialVersionUID = -5718084560573378145L;

	// 提示信息 TODO 普通信息和需要弹出信息现在都在这里，是否有问题
	protected String[] alertMessage;

	/** msg:简单弹框，时间到消失, alert：需要用户手动进行点击操作 */
	protected String alertType;
	/** 控制是否弹出信息 */
	protected Boolean notAlertFlag;

	// 异常信息
	protected String exception;
	// 返回的数据
	protected T data;
	// 请求是否成功
	protected boolean success = true;
	// 要调整的页面
	protected String goPage;

	/**
	 * 标志位置为成功，并且弹出“操作成功”窗口
	 */
	public void success() {
		this.setSuccess(true);
		this.setAlertMessage(new String[] { "操作成功!" });
	}

	/**
	 * 标志位置为失败，并且弹出“操作失败请联系管理员”窗口
	 */
	public void failed() {
		this.setSuccess(false);
		this.setAlertMessage(new String[] { "操作失败请联系管理员!" });
	}

	public String[] getAlertMessage() {
		return alertMessage;
	}

	public void addAlertMessage(final String alertMessage) {
		if (this.alertMessage == null) {
			this.alertMessage = new String[] { alertMessage };
		} else {
			String[] newArray = new String[alertMessage.length() + 1];
			System.arraycopy(alertMessage, 0, newArray, 0, alertMessage.length());
			newArray[newArray.length - 1] = alertMessage;
			this.alertMessage = newArray;
		}
	}

	public void setAlertMessage(final String alertMessage[]) {
		this.alertMessage = alertMessage;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public Boolean getNotAlertFlag() {
		return notAlertFlag;
	}

	public void setNotAlertFlag(Boolean notAlertFlag) {
		this.notAlertFlag = notAlertFlag;
	}

	public String getException() {
		return exception;
	}

	public void setException(final String exception) {
		this.exception = exception;
	}

	public T getData() {
		return data;
	}

	public void setData(final T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getGoPage() {
		return goPage;
	}

	public void setGoPage(final String goPage) {
		this.goPage = goPage;
	}

	public BaseResult<T> setSuccess(final boolean success) {
		this.success = success;
		return this;
	}
}
