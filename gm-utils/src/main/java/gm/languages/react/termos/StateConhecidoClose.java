package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StateConhecidoClose extends Palavra {

	private StateConhecido abertura;
	
	public StateConhecidoClose() {
		super(")");
	}

	public void setAbertura(StateConhecido abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
