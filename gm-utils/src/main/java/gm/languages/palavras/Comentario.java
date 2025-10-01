package gm.languages.palavras;

import src.commom.utils.string.StringRight;

public class Comentario extends Palavra {

	public Comentario() {
		super("");
	}
	
	public void add(Palavra p) {
		if (p instanceof ComentarioBlocoClose) {
			setS(getS().replace("*/", "(*)/"));
		}
		setS(getS() + p.getS());
	}

	public boolean contentEquals(String s) {
		return StringRight.ignore(getS().substring(2), 2).trim().contentEquals(s);
	}

}
