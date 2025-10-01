package gm.languages.java.expressoes;

import gm.languages.comum.DeclaracaoDeVariavelEscopo;
import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DeclaracaoDeVariavelRef extends Palavra {
	
	public static DeclaracaoDeVariavelRef procurada;

	private final DeclaracaoDeVariavel dec;
	private boolean comThis;
	
	@Setter
	private String thisText = "this.";

	public DeclaracaoDeVariavelRef(DeclaracaoDeVariavel dec) {
		super("");
		
		this.dec = dec;
		dec.refs.add(this);
		
		if (dec.getNome().eq("m")) {
			procurada = this;
		}
		
	}
	
	@Override
	public String getS() {
		String s = dec.getNome().getS();
		if (comThis) {
			s = thisText + s;
		}
		return s;
	}
	
	public void setComThis() {
		
		if (dec.getEscopo() == DeclaracaoDeVariavelEscopo.atributo) {
			comThis = true;
		} else {
			throw new RuntimeException();
		}
		
	}

}
