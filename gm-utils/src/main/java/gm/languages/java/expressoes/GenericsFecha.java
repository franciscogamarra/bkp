package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class GenericsFecha extends Palavra {

	private final GenericsAbre abertura;
	
	public GenericsFecha(GenericsAbre abertura) {
		super(">");
		this.abertura = abertura;
	}

}
