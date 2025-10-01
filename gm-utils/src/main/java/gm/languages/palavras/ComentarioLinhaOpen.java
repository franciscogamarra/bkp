package gm.languages.palavras;

public class ComentarioLinhaOpen extends Palavra {

	public ComentarioLinhaOpen() {
		super("");
	}

	@Override
	public String getS() {
		return getLinguagem().getComentarioLinhaOpen().getS();
	}

}
