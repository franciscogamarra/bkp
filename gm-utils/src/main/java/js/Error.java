package js;

import gm.utils.anotacoes.Ignorar;

@Ignorar
public class Error extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public String message;

	public Error(String message) {
		super(message);
		this.message = message;
	}

}
