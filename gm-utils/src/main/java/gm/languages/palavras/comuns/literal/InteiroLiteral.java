package gm.languages.palavras.comuns.literal;

import src.commom.utils.integer.IntegerParse;

public class InteiroLiteral extends Literal {

	public InteiroLiteral(String s) {
		super(s);
	}
	
	public int toInt() {
		return IntegerParse.toInt(getS());
	}
	
	public void negativar() {
		setS("-" + getS());
	}

}
