package gm.languages.sql.expressoes;

import gm.languages.sql.palavras.Exists;
import gm.languages.sql.palavras.Not;

public class NotExists extends FuncaoBooleana {

	public NotExists(Not not, Exists exists) {
		super(not.getS() + " " + exists.getS());
	}

}
