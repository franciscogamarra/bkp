package gm.languages.palavras.comuns.simples;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreParenteses extends Palavra {

	private FechaParenteses fechamento;
	
	public AbreParenteses() {
		super("(");
	}
	
	public void setFechamento(FechaParenteses fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}

}
