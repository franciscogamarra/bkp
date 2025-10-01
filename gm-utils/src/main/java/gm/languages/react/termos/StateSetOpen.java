package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class StateSetOpen extends Palavra {
	
	private StateSetClose fechamento;

	public StateSetOpen(String nome) {
		super(nome + ".set(");
	}
	
	public void setFechamento(StateSetClose fechamento) {
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
