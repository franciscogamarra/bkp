package gm.languages.ts.expressoes;

import gm.languages.palavras.Palavra;

public class CastTs extends Palavra {

	private TipoTs tipo;

	public CastTs(TipoTs tipo) {
		super(null);
		this.tipo = tipo;
	}
	
	@Override
	public String getS() {
		return " as " + tipo.getS();
	}

}
