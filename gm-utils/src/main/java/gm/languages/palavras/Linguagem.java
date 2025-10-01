package gm.languages.palavras;

import gm.languages.java.expressoes.MenosMenos;
import gm.languages.palavras.java.BarraBarra;
import lombok.Getter;

@Getter
public class Linguagem {

	private final Palavra comentarioLinhaOpen;
	private final Class<? extends Palavra> comentarioLinhaOpenClass;
	private final boolean stringComAspasSimples;
	private final boolean stringComAspasDuplas;
	private final boolean stringComCrase;
	private final String nome;

	private Linguagem(String nome, Palavra comentarioLinhaOpen, boolean stringComAspasSimples, boolean stringComAspasDuplas, boolean stringComCrase) {
		this.nome = nome;
		this.comentarioLinhaOpen = comentarioLinhaOpen;
		this.stringComAspasSimples = stringComAspasSimples;
		this.stringComAspasDuplas = stringComAspasDuplas;
		this.stringComCrase = stringComCrase;
		this.comentarioLinhaOpenClass = comentarioLinhaOpen.getClass();
	}
	
	public static final Linguagem java = new Linguagem("java", new BarraBarra(), false, true, false);
	public static final Linguagem sql = new Linguagem("sql", new MenosMenos(), true, false, false);
	public static final Linguagem js = new Linguagem("js", new BarraBarra(), true, true, false);
	public static final Linguagem as = new Linguagem("as", new BarraBarra(), true, true, false);
//	public static final Linguagem pascal = new Linguagem("pascal", new BarraBarra(), true, true, false);

	public static Linguagem selected = java;
	
	@Override
	public String toString() {
		return nome;
	}
	
}
