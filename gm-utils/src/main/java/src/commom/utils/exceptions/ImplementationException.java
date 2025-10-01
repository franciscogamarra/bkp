package src.commom.utils.exceptions;

public class ImplementationException extends js.Error {

//	@IgnoreStart
	private static final long serialVersionUID = 1L;

	public ImplementationException(String message) {
		super(message);
	}
//	@IgnoreEnd

	@Override
	public String getMessage() {
		return message;
	}

}
