package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class JsonParamOpen extends Palavra {
	
	private JsonParamClose fechamento;

	public JsonParamOpen(String nome) {
		super("." + nome.trim() + "(");
	}
	
	public void setFechamento(JsonParamClose fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
	@Override
	public int getEspacos() {
		return 0;
	}

}
