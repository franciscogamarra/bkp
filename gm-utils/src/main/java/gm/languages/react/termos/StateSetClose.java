package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StateSetClose extends Palavra {

	private StateSetOpen abertura;
	
	public StateSetClose() {
		super(")");
	}

	public void setAbertura(StateSetOpen abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
