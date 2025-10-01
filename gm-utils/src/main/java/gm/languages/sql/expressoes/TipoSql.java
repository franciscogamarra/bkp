package gm.languages.sql.expressoes;

import gm.languages.palavras.PalavraReservada;

public abstract class TipoSql extends PalavraReservada {

	@Override
	public String toString() {
		
		if (emAnalise) {
			return "Tipo["+getS()+"]";
		}
		
		return getS();
		
	}

}
