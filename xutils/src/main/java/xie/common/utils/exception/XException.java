package xie.common.utils.exception;

public class XException extends Exception {

	private static final long serialVersionUID = 7727925294050060709L;

	public XException() {
		super();
	}

	public XException(String message) {
		super(message);
	}

	public XException(String message, Throwable cause) {
		super(message, cause);
	}

	public XException(Throwable cause) {
		super(cause);
	}

}
