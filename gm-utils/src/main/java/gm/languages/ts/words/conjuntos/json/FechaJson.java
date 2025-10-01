package gm.languages.ts.words.conjuntos.json;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaJson extends Palavra {
	
	private AbreJson abertura;
	
	public FechaJson(AbreJson abertura) {
		super(" }");
		this.abertura = abertura;
	}

	@Override
	public String getS() {
		if (hasQuebra()) {
			return "}";
		} else {
			return " }";
		}
	}
	
}
