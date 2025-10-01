package gm.languages.sql.expressoes.cte;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class CteReference extends Palavra {

	public CteReference(Cte cte) {
		super(cte);
	}

}
