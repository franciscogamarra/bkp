package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.Null;
import gm.languages.sql.palavras.Is;

public class IsNull extends Palavra {

	public IsNull(Is is, Null n) {
		super(is.getS() + " " + n.getS());
	}
	

}
