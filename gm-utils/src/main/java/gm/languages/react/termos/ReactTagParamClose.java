package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReactTagParamClose extends Palavra {

	private ReactTagParamOpen abertura;
	
	public ReactTagParamClose() {
		super(")");
	}

	@Override
	public String toString() {
		return ")";
	}
	
	public void setAbertura(ReactTagParamOpen abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
