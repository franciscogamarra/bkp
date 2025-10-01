package gm.languages.palavras.comuns;

import gm.languages.palavras.Linguagem;
import gm.languages.palavras.OperadorComparacao;
import gm.utils.exception.NaoImplementadoException;

public class Diferente extends OperadorComparacao {

	public Diferente() {
		super("");
	}
	
	@Override
	public String getS() {
		
		if (getLinguagem() == Linguagem.sql) {
			return "<>";
		}

		if (getLinguagem() == Linguagem.java) {
			return "!=";
		}

		if (getLinguagem() == Linguagem.as) {
			return "!=";
		}
		
		if (getLinguagem() == Linguagem.js) {
			return "!==";
		}
		
		throw new NaoImplementadoException();
		
	}

}
