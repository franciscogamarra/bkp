package gm.languages.ts.conjuntos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinClose extends Palavra {

	private JoinOpen abertura;
	
	public JoinClose(JoinOpen abertura) {
		super("}");
		this.abertura = abertura;
	}

}
