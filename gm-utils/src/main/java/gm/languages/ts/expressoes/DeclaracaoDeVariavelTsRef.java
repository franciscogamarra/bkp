package gm.languages.ts.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DeclaracaoDeVariavelTsRef extends Palavra {
	
	private final DeclaracaoDeVariavelTs dec;
	
	@Setter
	private TipoTs classe;
	
	@Setter
	private boolean semThis;

	public DeclaracaoDeVariavelTsRef(DeclaracaoDeVariavelTs dec) {
		super("");
		this.dec = dec;
		dec.refs.add(this);
	}
	
	@Override
	public String getS() {
		
		String s = dec.getNome();
		
		if (semThis) {
			return s;
		}
		
		if (dec.isAtributo()) {
			
			if (dec.isStatic()) {
				return classe.getClasse().getSimpleName() + "." + s;
			}
			return "this." + s;
			
		}
		
		return s;
		
	}
	
	public boolean isThis() {
		return getS().startsWith("this.");
	}

}
