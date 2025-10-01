package gm.languages.ts.words.conjuntos.array;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaColcheteField extends Palavra {

	private AbreColcheteField abertura;
	
	public FechaColcheteField(AbreColcheteField abertura) {
		super("]");
		this.abertura = abertura;
	}

}
