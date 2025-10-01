package gm.languages.ts.conjuntos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinOpen extends Palavra {

	private final JoinClose fechamento = new JoinClose(this);
	
	public JoinOpen() {
		super("{");
	}
	
}