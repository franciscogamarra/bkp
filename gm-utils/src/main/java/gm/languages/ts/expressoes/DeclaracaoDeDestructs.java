package gm.languages.ts.expressoes;

import gm.languages.palavras.Palavra;
import gm.utils.comum.Lst;

public class DeclaracaoDeDestructs extends Palavra {

	private final Lst<DeclaracaoDeVariavelTs> decs = new Lst<>();
	
	public DeclaracaoDeDestructs() {
		super("");
	}
	
	@Override
	public String getS() {
		return "const [" + decs.toString(i -> i.getNome(), ", ") + "]";
	}
	
	public void add(DeclaracaoDeVariavelTs dec) {
		decs.add(dec);
	}
	
}
