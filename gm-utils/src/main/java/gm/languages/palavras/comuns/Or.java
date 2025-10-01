package gm.languages.palavras.comuns;

import gm.languages.palavras.Linguagem;
import gm.utils.exception.NaoImplementadoException;

public class Or extends AndOr {

	@Override
	public String getS() {
		
		if (getLinguagem() == Linguagem.sql) {
			return "or";
		}

		if (getLinguagem() == Linguagem.java || getLinguagem() == Linguagem.js) {
			return "||";
		}

		throw new NaoImplementadoException();
		
	}

}
