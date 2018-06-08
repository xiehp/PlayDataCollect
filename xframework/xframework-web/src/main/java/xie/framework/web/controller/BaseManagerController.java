package xie.framework.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xie.common.spring.jpa.entity.IdEntity;
import xie.common.spring.jpa.service.BaseService;
import xie.common.utils.java.XReflectionUtils;
import xie.common.utils.string.XStringUtils;
import xie.framework.web.ajax.vo.GoPageResult;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

public abstract class BaseManagerController<M extends IdEntity, ID extends Serializable> extends BaseController {

	protected abstract BaseService<M, ID> getBaseService();

//	@RequiresPermissions(value = "userList:add") // TODO shiro security
	@RequestMapping(value = "/updateOneColumn")
	@ResponseBody
	public GoPageResult updateOneColumn(@RequestParam ID id, @RequestParam String columnName, @RequestParam String columnValue, HttpServletRequest request) {

		GoPageResult goPageResult = null;

		M baseEntity = getBaseService().findOne(id);

		String methodName = "set" + XStringUtils.upperFirstLetter(columnName);

		if (XStringUtils.isEmpty(columnValue)) {
			columnValue = null;
		}
		invokeMethod(baseEntity, methodName, columnValue);

		baseEntity = getBaseService().save(baseEntity);

		goPageResult = goPageUtil.createSuccess(request, null);

		return goPageResult;
	}

	private void invokeMethod(Object baseEntity, String methodName, String columnValue) {
		Class<?>[] typeClasses = new Class<?>[] { String.class, Integer.class, Long.class, Date.class };
		Class<?> typeClass = null;
		Method setColumnMethod = null;
		for (Class<?> type : typeClasses) {
			if (setColumnMethod != null) {
				break;
			}

			typeClass = type;
			setColumnMethod = XReflectionUtils.findMethod(baseEntity.getClass(), methodName, typeClass);
		}

		if (setColumnMethod == null) {
			throw new RuntimeException("未找到方法：" + baseEntity + ", " + methodName);
		}

		Object value = XStringUtils.convert(columnValue, typeClass);
		XReflectionUtils.invokeMethod(setColumnMethod, baseEntity, value);
	}

}
