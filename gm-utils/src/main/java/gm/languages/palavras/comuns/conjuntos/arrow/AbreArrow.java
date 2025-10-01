package gm.languages.palavras.comuns.conjuntos.arrow;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreArrow extends Palavra {

	private final FechaArrow fechamentoArrow = new FechaArrow(this);
	private Arrow arrow;
	
	public AbreArrow(Arrow arrow) {
		super("");
		this.arrow = arrow;
	}

	@Override
	public String getS() {
		return arrow.isAsync() ? "async" : "";
	}
	
}