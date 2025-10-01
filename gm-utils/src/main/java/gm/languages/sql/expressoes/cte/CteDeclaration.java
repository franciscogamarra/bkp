package gm.languages.sql.expressoes.cte;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class CteDeclaration extends Palavra {

	private Cte cte;

	public CteDeclaration(Cte cte) {
		super("with " + cte.getNome() + " as");
		this.cte = cte;
	}

}
