package gm.languages.ts.words;

import gm.languages.palavras.PalavraReservada;
import gm.languages.ts.expressoes.TipoTs;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImportTs extends PalavraReservada {
	
	private TipoTs classe;
	
	@Override
	public String getS() {
		
		if (classe == null) {
			return "import";
		}
		
		return "import " + classe.getClasse().getSimpleName();
	}
}