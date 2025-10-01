package gm.languages.sql.expressoes.variaveis;

import lombok.Getter;

@Getter
public class Variavel {
	
	private String nome;
	
	public Variavel(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}

}