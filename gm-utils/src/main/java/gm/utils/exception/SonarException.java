package gm.utils.exception;

public class SonarException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SonarException() {
	}

	public SonarException(String s) {
		super(s);
	}
	
	public SonarException(Class<?> classe) {
		this(classe.getName());
	}

}