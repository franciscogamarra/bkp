package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaParenteses extends Palavra {

	private AbreParenteses abertura;
	
	public FechaParenteses() {
		super(")");
	}

	@Override
	public String toString() {
		return ")";
	}
	
	public void setAbertura(AbreParenteses abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
