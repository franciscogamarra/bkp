package gm.languages.sql.expressoes;

import gm.languages.sql.palavras.Join;
import gm.languages.sql.palavras.Left;

public class LeftJoin extends JoinAny {

	public LeftJoin(Left left, Join join) {
		super(left.getS() + " " + join.getS());
	}


}
