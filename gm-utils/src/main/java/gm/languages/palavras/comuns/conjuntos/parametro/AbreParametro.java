package gm.languages.palavras.comuns.conjuntos.parametro;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreParametro extends Palavra {

	private final FechaParametro fechamento = new FechaParametro(this);
	private String nome;
	private boolean vazio;

	public AbreParametro(String nome) {
		super("");
		this.nome = nome;
	}
	
	@Override
	public String getS() {
		
		if (vazio) {
			return nome;
		}
		
		if ("reticencias".contentEquals(nome)) {
			return "{...";
		}
		
		return nome + "={";
	}

	@Override
	public int getEspacos() {
		
		if (getQuebras() == 0) {
			return 1;
		} else {
			return 0;
		}
		
	}
	
}
