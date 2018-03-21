package xie.common.spring.utils;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class XMessageSourceUtils {

	@Resource
	private MessageSource messageSource;

	public String getMessage(String key) {
		return getMessage(key, null, null);
	}

	public String getMessage(String key, Locale locale) {
		return getMessage(key, locale, null);
	}

	public String getMessage(String key, Locale locale, Object[] args) {
		if (key == null) {
			return null;
		}

		return messageSource.getMessage(key, args, locale);
	}
}
