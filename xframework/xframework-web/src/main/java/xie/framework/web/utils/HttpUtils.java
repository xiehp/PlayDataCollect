package xie.framework.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static boolean needJsonResponse(HttpServletRequest request) {
		// 非异步请求，直接跳转到错误页面
		boolean ajaxFlg = false;
		logger.debug("extend_param_josn_response:" + request.getParameter("extend_param_josn_response"));
		if ("true".equals(request.getParameterMap().get("extend_param_josn_response"))) {
			// 方法内设置了ajax提交标志，则一定是ajax
			ajaxFlg = true;
		} else {
			if ((request.getHeader("accept").indexOf("application/json") > -1)) {
				ajaxFlg = true;
			}
			if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) {
				ajaxFlg = true;
			}
		}

		return ajaxFlg;
	}






}
