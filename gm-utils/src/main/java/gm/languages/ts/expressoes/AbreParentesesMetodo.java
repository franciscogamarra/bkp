package gm.languages.ts.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreParentesesMetodo extends Palavra {

	private DeclaracaoDeMetodoTs metodo;
	
	public AbreParentesesMetodo(DeclaracaoDeMetodoTs metodo) {
		super("(");
		this.metodo = metodo;
		metodo.setAbreParentesesMetodo(this);
	}
	
	public FechaParentesesMetodo getFechamento() {
		return getMetodo().getFechaParentesesMetodo();
	}
	
}
