package gm.languages.palavras.comuns.conjuntos.parametro;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParentesesCondicaoClose extends Palavra {

	private ParentesesCondicaoOpen abertura;
	
	public ParentesesCondicaoClose() {
		super(")");
	}

	@Override
	public String toString() {
		return ")";
	}
	
	public void setAbertura(ParentesesCondicaoOpen abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}
