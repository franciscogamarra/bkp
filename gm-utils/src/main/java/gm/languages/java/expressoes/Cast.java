package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cast extends Palavra {

	private final TipoJava tipo;
	private Palavra fechamento;

	public Cast(TipoJava tipo) {
		super(null);
		this.tipo = tipo;
	}
	
	@Override
	public String getS() {
		
		if (isAs()) {
			return " as "+tipo.getS();
		}
		
		return "("+tipo.getS()+")";
	}

}
