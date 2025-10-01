package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.sql.palavras.Select;

public class SelectEnd extends Palavra {

	private Select select;

	public SelectEnd(Select select) {
		super("");
		this.select = select;
	}

	@Override
	public String toString() {
		
		if (emAnalise) {
			return "<End of "+select+">";
		}
		
		return "";
		
	}
	
}
