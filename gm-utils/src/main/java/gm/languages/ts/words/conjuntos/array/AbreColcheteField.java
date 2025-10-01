package gm.languages.ts.words.conjuntos.array;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreColcheteField extends Palavra {

	private final FechaColcheteField fechamento = new FechaColcheteField(this);
	
	public AbreColcheteField() {
		super("[");
	}
	
}