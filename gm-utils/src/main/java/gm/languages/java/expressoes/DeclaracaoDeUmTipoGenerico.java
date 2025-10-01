package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class DeclaracaoDeUmTipoGenerico extends Palavra {

	private TipoJava tipo;

	public DeclaracaoDeUmTipoGenerico(TipoJava tipo) {
		super(tipo);
		this.tipo = tipo;
	}
	
	@Override
	public String getS() {
		return "<" + tipo.getS() + ">";
	}

}
