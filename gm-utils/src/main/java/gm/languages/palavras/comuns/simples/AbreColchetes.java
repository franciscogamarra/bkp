package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreColchetes extends Palavra {

	private FechaColchetes fechamento;

	public AbreColchetes() {
		super("[");
	}
	
	@Override
	public String toString() {
		return "[";
	}

}
