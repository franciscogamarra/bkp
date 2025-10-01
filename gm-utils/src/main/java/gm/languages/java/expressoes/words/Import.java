package gm.languages.java.expressoes.words;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.PalavraReservada;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Import extends PalavraReservada {
	
	private TipoJava tipo;
	
	@Override
	public String getS() {
		
		if (tipo == null) {
			return "import";
		}
		
		return "import " + tipo.getName() + ";";
		
	}
	
	@Override
	public String toString() {
		return getS();
	}
	
}