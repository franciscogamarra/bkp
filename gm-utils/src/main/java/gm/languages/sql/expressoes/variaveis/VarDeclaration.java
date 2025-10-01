package gm.languages.sql.expressoes.variaveis;

import gm.languages.palavras.Palavra;
import gm.languages.sql.expressoes.TipoSql;
import lombok.Getter;

@Getter
public class VarDeclaration extends Palavra {

	private Variavel o;
	private TipoSql tipo;

	public VarDeclaration(Variavel v, TipoSql tipo) {
		super("");
		this.o = v;
		this.tipo = tipo;
	}
	
	@Override
	public String getS() {
		return o.getNome() + " " + tipo;
	}

}
