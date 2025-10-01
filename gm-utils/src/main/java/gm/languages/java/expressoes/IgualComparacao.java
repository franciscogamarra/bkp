package gm.languages.java.expressoes;

import gm.languages.palavras.Linguagem;
import gm.languages.palavras.OperadorComparacao;
import gm.utils.exception.NaoImplementadoException;

public class IgualComparacao extends OperadorComparacao {

	public IgualComparacao() {
		super("");
	}
	
	@Override
	public String getS() {
		
		if (getLinguagem() == Linguagem.sql) {
			return "=";
		}
		
		if (getLinguagem() == Linguagem.java) {
			return "==";
		}

		if (getLinguagem() == Linguagem.js) {
			return "===";
		}

		throw new NaoImplementadoException();
		
	}
	
}
