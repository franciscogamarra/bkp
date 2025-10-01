package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaChaves extends Palavra {

	private AbreChaves abertura;
	
	public FechaChaves() {
		super("}");
	}
	
	@Override
	public String toString() {
		return "}";
	}
	
}
