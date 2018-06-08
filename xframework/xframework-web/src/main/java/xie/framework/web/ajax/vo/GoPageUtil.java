package xie.framework.web.ajax.vo;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;
import xie.common.spring.utils.XMessageSourceUtils;
import xie.common.utils.string.XStringUtils;
import xie.framework.web.utils.Constants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取GoPageResult工具类
 */
@Component
public class GoPageUtil {

	@Resource
	XMessageSourceUtils messageSourceUtils;

	public static GoPageResult getNullResult() {
		return getResult(null, null, null, null);
	}

	public static GoPageResult getResult(final String goPage) {
		return getResult(null, goPage, null, null);
	}

	public static GoPageResult getResult(final Object data) {
		return getResult(null, null, null, data);
	}

	public static GoPageResult getResult(final String goPage, final Object data) {
		return getResult(null, goPage, null, data);
	}

	private static GoPageResult getResult(String message, final String goPage, final String openerGoPage, final Object data) {
		return getResultByMessage(message == null ? null : new String[] { message }, null, goPage, openerGoPage, data);
	}

	public static GoPageResult getResultByMessage(final String[] alertMessage, final String exception, final String goPage, final String openerGoPage, final Object data) {
		final GoPageResult goPageResult = new GoPageResult();

		goPageResult.setAlertMessage(alertMessage);
		goPageResult.setData(data);
		if (XStringUtils.isBlank(exception)) {
			goPageResult.setException(null);
		} else {
			goPageResult.setException("server occur exception");
		}
		goPageResult.setGoPage(goPage);
		goPageResult.setSuccess(true);

		return goPageResult;
	}

	/**
	 * 创建成功的返回json数据，带弹出”操作成功“提示框
	 */
	public GoPageResult createSuccess(HttpServletRequest request) {
		return createSuccess(request, "操作成功");
	}

	public GoPageResult createSuccess(HttpServletRequest request, String message) {
		return createGoPageResult(request, true, message);
	}

	/**
	 * 创建成功的返回json数据，带弹出”操作失败“提示框
	 */
	public GoPageResult createFail(HttpServletRequest request) {
		return createFail(request, "操作失败");
	}

	public GoPageResult createFail(HttpServletRequest request, String message) {
		return createGoPageResult(request, false, message);
	}

	/**
	 * message会进行多语言转换
	 * 
	 * @param success
	 * @param message
	 * @return
	 */
	public GoPageResult createGoPageResult(HttpServletRequest request, boolean success, String message) {
		String newMessage = messageSourceUtils.getMessage(message, RequestContextUtils.getLocale(request));
		return createGoPageResultStatic(success, newMessage);
	}

	/**
	 * 静态方法，message不会进行多语言转换
	 * 
	 * @param success
	 * @param message
	 * @return
	 */
	public static GoPageResult createGoPageResultStatic(boolean success, String message) {
		GoPageResult goPageResult = new GoPageResult();
		goPageResult.setSuccess(success);
		if (message != null) {
			goPageResult.setAlertMessage(new String[] { message });
		}

		// 老的框架对应
		goPageResult.setCode(success ? Constants.SUCCESS_CODE : Constants.FAIL_CODE);
		goPageResult.setMessage(message);

		return goPageResult;
	}
}
