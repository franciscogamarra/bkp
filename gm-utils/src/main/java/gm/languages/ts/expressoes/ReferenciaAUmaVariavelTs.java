package gm.languages.ts.expressoes;

import gm.languages.comum.DeclaracaoDeVariavelEscopo;
import gm.languages.palavras.Palavra;

public class ReferenciaAUmaVariavelTs extends Palavra {

	private DeclaracaoDeVariavelTs variavel;

	public ReferenciaAUmaVariavelTs(DeclaracaoDeVariavelTs variavel) {
		super("");
		this.variavel = variavel;
	}
	
	@Override
	public String getS() {
		String s = variavel.getNome();
		if (variavel.getEscopo() == DeclaracaoDeVariavelEscopo.atributo) {
			s = "this." + s;
		}
		return s;
	}

}
