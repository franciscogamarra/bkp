package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreChaves extends Palavra {
	
	private FechaChaves fechamento;

	public AbreChaves() {
		super("{");
	}
	
	@Override
	public String toString() {
		return "{";
	}
	
	public void setFechamento(FechaChaves fechamento) {
		this.fechamento = fechamento;
	}

}
