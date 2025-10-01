package gm.languages.palavras.comuns;

import gm.languages.palavras.Palavra;

public class NaoClassificada extends Palavra {

	public NaoClassificada(String s) {
		super(s);
	}
	
	@Override
	public String toString() {
		
		if (emAnalise) {
			return "\t" + super.toString();
		}
		
		return super.toString();
	}

}
