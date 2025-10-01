package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class CallOpen extends Palavra {
	
	private CallClose fechamento;

	public CallOpen() {
		super(".call(");
	}
	
	public void setFechamento(CallClose fechamento) {
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
