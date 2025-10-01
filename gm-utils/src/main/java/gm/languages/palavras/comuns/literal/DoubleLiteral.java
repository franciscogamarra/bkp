package gm.languages.palavras.comuns.literal;

import gm.utils.number.UDouble;

public class DoubleLiteral extends Literal {

	public DoubleLiteral(InteiroLiteral a, InteiroLiteral b) {
		super(a.getS() + "." + b.getS());
	}

	public DoubleLiteral(LongLiteral a, LongLiteral b) {
		super(a.getS() + "." + b.getS());
	}
	
	public double toDouble() {
		return UDouble.toDouble(getS());
	}

}
