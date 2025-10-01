package gm.languages.ts.expressoes;

import gm.languages.java.expressoes.DeclaracaoDeUmTipoGenerico;
import gm.languages.palavras.Palavra;

public class DeclaracaoDeUmTipoGenericoTs extends Palavra {

	private TipoTs tipo;

	public DeclaracaoDeUmTipoGenericoTs(TipoTs tipo) {
		super(tipo);
		this.tipo = tipo;
	}
	
	@Override
	public String getS() {
		return "<" + tipo.getS() + ">";
	}

	public static DeclaracaoDeUmTipoGenericoTs build(DeclaracaoDeUmTipoGenerico dec) {

		if (dec == null) {
			return null;
		}
		
		TipoTs tp = TipoTs.build(dec.getTipo());
		return new DeclaracaoDeUmTipoGenericoTs(tp);
			
	}

}
