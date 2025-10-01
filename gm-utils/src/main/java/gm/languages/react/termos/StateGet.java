package gm.languages.react.termos;

import gm.languages.palavras.Palavra;

public class StateGet extends Palavra {
	public final String nome;
	public StateGet(String nome) {
		super(nome + ".get()");
		this.nome = nome;
	}
}
