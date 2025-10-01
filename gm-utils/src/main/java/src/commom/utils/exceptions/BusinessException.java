package src.commom.utils.exceptions;

import gm.utils.anotacoes.Ignorar;

public class BusinessException extends js.Error {

//	@IgnoreStart
	@Ignorar
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}
//	@IgnoreEnd

	@Override
	public String getMessage() {
		return message;
	}

}
