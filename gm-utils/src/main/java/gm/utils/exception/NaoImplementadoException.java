package gm.utils.exception;

public class NaoImplementadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NaoImplementadoException() {
	}

	public NaoImplementadoException(String s) {
		super(s);
	}
	
	public NaoImplementadoException(Class<?> classe) {
		this(classe.getName());
	}

}