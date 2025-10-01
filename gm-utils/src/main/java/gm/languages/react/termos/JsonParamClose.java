package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JsonParamClose extends Palavra {

	private JsonParamOpen abertura;
	
	public JsonParamClose() {
		super(")");
	}

	public void setAbertura(JsonParamOpen abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
