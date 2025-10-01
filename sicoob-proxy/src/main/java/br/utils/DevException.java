package br.utils;

public class DevException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private DevException(String message) {
		super(message);
	}
	
	private DevException(Throwable e) {
		super(e);
	}
	
	public static DevException build(Throwable e) {
		
		if (e instanceof DevException) {
			return (DevException) e;
		}
		
		if (e.getCause() != null && e.getCause() != e) {
			return build(e.getCause());
		}
		
		return new DevException(e);
		
	}
	
	public static DevException build(String message) {
		return new DevException(message);
	}
	
}
