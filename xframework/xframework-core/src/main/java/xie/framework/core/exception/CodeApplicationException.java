package xie.framework.core.exception;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class CodeApplicationException extends Exception {
	private static MessageSource messageSource;

	private static final long serialVersionUID = -114729666217638379L;

	// public final static CodeApplicationException SUCCESS = new CodeApplicationException(PropsKeys.TBRESOURCES_MESSAGE_ID_SUCCESS);
	// public final static CodeApplicationException FAILURE = new CodeApplicationException(PropsKeys.TBRESOURCES_MESSAGE_ID_FAILURE);

	// 代码
	private String code;

	// 参数
	private String[] param;

	// 可抛异常
	private Throwable parentThrowable;

	public static void setMessageSource(MessageSource messageSource) {
		CodeApplicationException.messageSource = messageSource;
	}

	/**
	 * 构造函数
	 * 
	 * @param code 代码
	 * @param param 参数
	 * @param nestedThrowable 可抛异常
	 */
	public CodeApplicationException(final String code, final String[] param, final Throwable nestedThrowable) {
		super();
		this.code = code;
		this.param = param;
		parentThrowable = nestedThrowable;
	}

	/**
	 * 构造函数
	 * 
	 * @param code 代码
	 * @param param 参数
	 */
	public CodeApplicationException(final String code, final String[] param) {
		this(code, param, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param code 代码
	 * @param nestedThrowable 可抛异常
	 */
	public CodeApplicationException(final String code, final Throwable nestedThrowable) {
		this(code, null, nestedThrowable);
	}

	/**
	 * 构造函数
	 * 
	 * @param code 代码
	 */
	public CodeApplicationException(final String code) {
		this(code, null, null);
	}

	public final String getCode() {
		return code;
	}

	public final String[] getParam() {
		return param;
	}

	public final Throwable getParentThrowable() {
		return parentThrowable;
	}

	public String getMessage() {
		return getMessage(Locale.getDefault());
	}

	public String getMessage(Locale locale) {
		String message = getCode();
		if (messageSource != null) {
			message = messageSource.getMessage(getCode(), param, locale);
		}
		return message;
	}
}
