package gm.languages.palavras;

import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.FechaParenteses;

public class Parenteses extends Palavras {
	
	public void extrai(AbreParenteses abre, Palavras itens) {
		
		add(abre);
		
		int opens = 1;
		
		while (opens > 0) {
			
			Palavra o = itens.removeAfter(abre);
			
			add(o);
			
			if (o instanceof AbreParenteses) {
				opens++;
			} else if (o instanceof FechaParenteses) {
				opens--;
			}
			
		}
		
		itens.remove(abre);
		
	}
	
	@Override
	public Palavras getItens() {
		return this;
	}

}
