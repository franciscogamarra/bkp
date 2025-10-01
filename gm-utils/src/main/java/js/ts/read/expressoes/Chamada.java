package js.ts.read.expressoes;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class Chamada extends Palavra {

	private TipoJava tipo;

	public Chamada(Object s, TipoJava tipo) {
		super(s);
		this.tipo = tipo;
	}

}
