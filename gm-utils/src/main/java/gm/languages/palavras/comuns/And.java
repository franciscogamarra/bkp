package gm.languages.palavras.comuns;

import gm.languages.palavras.Linguagem;
import gm.utils.exception.NaoImplementadoException;

public class And extends AndOr {

	@Override
	public String getS() {
		
		if (getLinguagem() == Linguagem.sql) {
			return "and";
		}

		if (getLinguagem() == Linguagem.java || getLinguagem() == Linguagem.js) {
			return "&&";
		}

		throw new NaoImplementadoException();
		
	}

}
