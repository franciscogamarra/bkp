package gm.languages.sql.palavras;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.PalavraReservada;
import gm.languages.palavras.Palavras;
import gm.languages.sql.expressoes.EndCase;
import gm.utils.comum.Lst;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Case extends PalavraReservada {
	
	private EndCase fechamento;

	public Lst<When> getWhens(Palavras palavras) {

		Lst<Palavra> itens = palavras.entre(this, fechamento);
		
		while (itens.filter(Case.class).isNotEmpty()) {
			Case cs = itens.filter(Case.class).getFirst();
			Lst<Palavra> outros = palavras.entre(cs, cs.fechamento);
			itens.remove(outros);
			itens.remove(cs);
			itens.remove(cs.getFechamento());
		}
		
		return itens.filter(When.class);
		
	}
}