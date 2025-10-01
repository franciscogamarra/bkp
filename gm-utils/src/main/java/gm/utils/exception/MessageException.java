package gm.utils.exception;

import gm.utils.string.CorretorOrtografico;

public class MessageException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MessageException (String mensagem){
		super(CorretorOrtografico.exec(mensagem));
	}

//	@Override
//	public void printStackTrace() {
//		try {
//			if (UConfig.get() != null && UConfig.get().emDesenvolvimento()) {
//				UException.trata(this);
//				super.printStackTrace();
//			}
//		} catch (Exception e) {}
//	}
//
//	@Override
//	public StackTraceElement[] getStackTrace() {
//
//		if (UConfig.get() != null && UConfig.get().emDesenvolvimento()) {
//
//		}
//
//		return super.getStackTrace();
//	}
//
	@Override
	public void printStackTrace() {}

	@Override
	public StackTraceElement[] getStackTrace() {
		return new StackTraceElement[0];
	}

}
