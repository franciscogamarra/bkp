package gm.languages.react.termos;

import gm.languages.palavras.Palavra;

public class StateIsEmpty extends Palavra {
	public final String nome;
	public StateIsEmpty(String nome) {
		super(nome + ".isEmpty()");
		this.nome = nome;
	}
}
