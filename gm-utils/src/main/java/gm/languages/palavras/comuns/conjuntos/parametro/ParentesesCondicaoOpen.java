package gm.languages.palavras.comuns.conjuntos.parametro;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParentesesCondicaoOpen extends Palavra {

	private ParentesesCondicaoClose fechamento;
	
	public ParentesesCondicaoOpen() {
		super("(");
	}
	
	public void setFechamento(ParentesesCondicaoClose fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}

}
