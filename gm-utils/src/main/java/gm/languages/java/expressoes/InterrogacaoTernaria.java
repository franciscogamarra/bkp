package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class InterrogacaoTernaria extends Palavra {
	
	private DoisPontosTernario fechamento;

	public InterrogacaoTernaria() {
		super("?");
	}
	
	public void setFechamento(DoisPontosTernario fechamento) {
		
		if (this.fechamento == fechamento) {
			return;
		}
		
		this.fechamento = fechamento;
		
		if (fechamento != null) {
			fechamento.setAbertura(this);
		}
		
	}

}
