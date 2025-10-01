package gm.languages.palavras;

import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringRight;

public class PalavraConhecida extends Palavra {

	public PalavraConhecida(String s) {
		super(s);
	}
	
	public PalavraConhecida() {
		this("");
		String s = StringPrimeiraMinuscula.exec(getClass().getSimpleName());
		if (s.endsWith("_")) {
			s = StringRight.ignore1(s);
		}
		setS(s);
	}
	
	@Override
	public String toString() {
		
		if (emAnalise) {
			return "<"+getClass().getSimpleName()+"("+getId()+")"+">";
		}
		
		return super.toString();
		
	}

}
