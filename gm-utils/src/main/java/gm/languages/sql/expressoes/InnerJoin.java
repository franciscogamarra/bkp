package gm.languages.sql.expressoes;

import gm.languages.sql.palavras.Inner;
import gm.languages.sql.palavras.Join;

public class InnerJoin extends JoinAny {

	public InnerJoin(Inner inner, Join join) {
		super(inner.getS() + " " + join.getS());
	}
	
	public InnerJoin(Join join) {
		super(join.getS());
	}


}
