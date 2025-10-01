package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaColchetes extends Palavra {
	
	private AbreColchetes abertura;

	public FechaColchetes() {
		super("]");
	}
	
	@Override
	public String toString() {
		return "]";
	}

}
