package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class GenericsAbre extends Palavra {
	
	private final GenericsFecha fechamento = new GenericsFecha(this);

	public GenericsAbre() {
		super("<");
	}

}
