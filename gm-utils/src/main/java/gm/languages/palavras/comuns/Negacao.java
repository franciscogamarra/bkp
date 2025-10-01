package gm.languages.palavras.comuns;

import gm.languages.palavras.Linguagem;
import gm.utils.exception.NaoImplementadoException;

public class Negacao extends AndOr {

	@Override
	public String getS() {
		
		if (getLinguagem() == Linguagem.java || getLinguagem() == Linguagem.js) {
			return "!";
		}

		if (getLinguagem() == Linguagem.sql) {
			return "not";
		}
		
		throw new NaoImplementadoException();
		
	}

}
