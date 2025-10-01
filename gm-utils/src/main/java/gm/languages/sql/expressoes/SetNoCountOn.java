package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.sql.palavras.NoCount;
import gm.languages.sql.palavras.On;
import gm.languages.sql.palavras.Set;

public class SetNoCountOn extends Palavra {

	public SetNoCountOn(Set set, NoCount noCount, On on) {
		super(set.getS() + " " + noCount.getS() + " " + on.getS());
	}

}
