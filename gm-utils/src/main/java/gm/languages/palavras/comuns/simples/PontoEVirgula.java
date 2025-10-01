package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;

public class PontoEVirgula extends Palavra {

	public PontoEVirgula() {
		super(";");
	}
	
	@Override
	public int getTabs() {
		return 0;
	}
	
	@Override
	public int getEspacos() {
		return 0;
	}
	
	@Override
	public int getQuebras() {
		return 0;
	}

}
