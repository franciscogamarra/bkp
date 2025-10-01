package gm.languages.palavras.comuns.conjuntos.arrow;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaArrow extends Palavra {

	private final AbreArrow abertura;
	
	public FechaArrow(AbreArrow abertura) {
		super("");
		this.abertura = abertura;
	}

	public Arrow getArrow() {
		return getAbertura().getArrow();
	}

}
