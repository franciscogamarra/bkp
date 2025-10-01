package gm.languages.sql.expressoes.variaveis;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class VarReference extends Palavra {

	private Variavel var;

	public VarReference(Variavel var) {
		super(var);
		this.var = var;
	}

}
