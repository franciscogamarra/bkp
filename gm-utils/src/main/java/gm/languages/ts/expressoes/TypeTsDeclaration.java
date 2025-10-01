package gm.languages.ts.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TypeTsDeclaration extends Palavra {

	private final TipoTs tipo;
	private AbreBloco abre;

	public TypeTsDeclaration(TipoTs tipo) {
		super("");
		this.tipo = tipo;
	}
	
	@Override
	public String getS() {
		return "type " + tipo.getS() + " = ";
	}

}
