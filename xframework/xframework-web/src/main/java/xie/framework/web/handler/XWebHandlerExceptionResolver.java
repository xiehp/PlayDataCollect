package xie.framework.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import xie.common.spring.utils.SpringUtils;
import xie.common.utils.string.XStringUtils;
import xie.framework.web.ajax.vo.GoPageResult;
import xie.framework.web.utils.Constants;
import xie.framework.web.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class XWebHandlerExceptionResolver implements HandlerExceptionResolver {

	private Logger _log = LoggerFactory.getLogger(this.getClass());

	// @Autowired
	// private RequestMappingHandlerMapping requestMappingHandlerMapping;

	private Map<String, HandlerMethod> path2HandlerMethodMap;
	private Map<String, Boolean> pathIsResponseBodyMap;

	// private static boolean LogFlag = GetterUtil.getBoolean(PropsUtil.getProperty(PropsKeys.SYSTEM_ERROR_LOG_ENABLED), false);

	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response, Object handler, Exception ex) {
		return processException(request, response, handler, ex, _log);
	}

	public ModelAndView processException(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex, Logger log) {

		if (handler == null) {
			handler = request.getAttribute("ControllerMethodHandler");
		}
		log.error(log.getName() + "截获住异常，开始处理");
		log.error("handler: {}", handler);

		String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
		String url = request.getRequestURL().toString() + query;
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		log.error("请求发生错误：contextPath: {}", contextPath);
		log.error("请求url: {}", url);
		try {
			Map<String, String[]> map = request.getParameterMap();
			Map<String, String> newMap = new HashMap<>();
			for (String key : map.keySet()) {
				String[] values = map.get(key);
				String newValue = null;
				if (values != null) {
					newValue = "\"";
					newValue += String.join("\", \"", values);
					newValue += "\"";
				}
				newMap.put(key, newValue);
			}
			log.error("请求参数: {}", newMap);
		} catch (Exception e) {
			log.error("获取请求参数发生异常: {}", e);
		}
		log.error("异常信息", ex);

		// 非异步请求，直接跳转到错误页面
		boolean ajaxFlg = HttpUtils.needJsonResponse(request);

		if (!ajaxFlg) {
			// Controller中的RequestMapping中明确返回ResponseBody
			if (isResponseBodyRequest(request, handler)) {
				ajaxFlg = true;
			}
		}

		if (!ajaxFlg) {
			ModelAndView modelAndView = new ModelAndView("error/500");
			if (uri.startsWith(contextPath + "/portal")) {
				modelAndView = new ModelAndView("portal/error/500");
			}
			modelAndView.addObject("exception", ex);
			return modelAndView;
		} else {
			GoPageResult goPageResult = new GoPageResult();
			goPageResult.setSuccess(false);
			String message = ex.getMessage();
			if (XStringUtils.isBlank(message)) {
				message = ex.toString();
			}
			message = ex.toString();
			goPageResult.setAlertMessage(new String[] { message });
			goPageResult.setException(ex.getClass().toString());
			goPageResult.setGoPage("");
			goPageResult.setCode(Constants.FAIL_CODE);
			goPageResult.setMessage(message);

			MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
			ModelAndView modelAndView = new ModelAndView(mappingJackson2JsonView);
			Map<String, Object> goPageResultMap = goPageResult.convertToObjectMap();
			modelAndView.addAllObjects(goPageResultMap);
			// modelAndView.addObject(JsonView.class.getName(), MappingJackson2JsonView.class);

			// MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(goPageResult);
			// modelAndView.addObject(goPageResult);
			// modelAndView.addObject("fff", "fff");
			// modelAndView.addObject("bbb", "bbb");
			// modelAndView.addObject("vvv", "vvv");
			return modelAndView;
		}

		// String uri = request.getRequestURI();
		//
		// if(ex instanceof SocketException){
		// _log.error("resolveException SocketException:"+uri);
		// return null;
		// }else if(ex.getClass().getCanonicalName().endsWith("ClientAbortException")){
		// //浏览器访问一半手动终止时，会出ClientAbortException
		// _log.error("resolveException ClientAbortException:"+uri);
		// return null;
		// }
		//
		// _log.error("url: {} ,resolveException error: {}", ex);

	}

	public synchronized Map<String, HandlerMethod> getPath2HandlerMethodMap() {
		if (path2HandlerMethodMap == null) {
			path2HandlerMethodMap = new LinkedHashMap<>();
			pathIsResponseBodyMap = new LinkedHashMap<>();
			RequestMappingHandlerMapping requestMappingHandlerMapping = null;
			try {
				requestMappingHandlerMapping = ContextLoader.getCurrentWebApplicationContext().getBean(RequestMappingHandlerMapping.class);
			} catch (Exception e) {
				_log.error("获取RequestMappingHandlerMapping错误", e);
			}
			if (requestMappingHandlerMapping == null) {
				try {
					requestMappingHandlerMapping = SpringUtils.getBean(RequestMappingHandlerMapping.class);
				} catch (Exception e) {
					_log.error("获取RequestMappingHandlerMapping错误", e);
				}
			}
			if (requestMappingHandlerMapping != null) {
				Map<RequestMappingInfo, HandlerMethod> handlerMethodsMap = requestMappingHandlerMapping.getHandlerMethods();

				handlerMethodsMap.keySet().forEach(requestMappingInfo -> {
					HandlerMethod handlerMethod = handlerMethodsMap.get(requestMappingInfo);
					requestMappingInfo.getPatternsCondition().getPatterns().forEach(s -> {
						path2HandlerMethodMap.put(s, handlerMethod);
						pathIsResponseBodyMap.put(s, isResponseBodyHandlerMethod(handlerMethod));
					});
				});

			}
		}

		return path2HandlerMethodMap;
	}

	public boolean isResponseBodyRequest(HttpServletRequest request, Object handler) {
		Boolean isResponseBody = getPathIsResponseBodyMap().get(request.getServletPath());
		if (isResponseBody != null) {
			return isResponseBody;
		} else {
			if (handler != null && handler instanceof HandlerMethod) {
				return isResponseBodyHandlerMethod((HandlerMethod) handler);
			}
		}

		// 默认
		return false;
	}

	public boolean isResponseBodyHandlerMethod(HandlerMethod handlerMethod) {
		ResponseBody[] responseBodyArray = handlerMethod.getMethod().getAnnotationsByType(ResponseBody.class);
		if (responseBodyArray != null && responseBodyArray.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized Map<String, Boolean> getPathIsResponseBodyMap() {
		getPath2HandlerMethodMap();
		return pathIsResponseBodyMap;
	}

}
