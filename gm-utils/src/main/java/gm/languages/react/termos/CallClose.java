package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CallClose extends Palavra {

	private CallOpen abertura;
	
	public CallClose() {
		super(")");
	}

	public void setAbertura(CallOpen abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
