package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.Null;
import gm.languages.sql.palavras.Is;
import gm.languages.sql.palavras.Not;

public class IsNotNull extends Palavra {

	public IsNotNull(Is is, Not not, Null n) {
		super(is.getS() + " " + not.getS() + " " + n.getS());
	}
	

}
