package gm.languages.sql.expressoes.cte;

import gm.languages.palavras.comuns.NaoClassificada;
import lombok.Getter;

@Getter
public class Cte {
	
	private String nome;
	
	public Cte(NaoClassificada nome) {
		this.nome = nome.getS();
	}
	
	@Override
	public String toString() {
		return nome;
	}

}