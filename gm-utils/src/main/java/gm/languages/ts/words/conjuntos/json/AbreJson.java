package gm.languages.ts.words.conjuntos.json;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreJson extends Palavra {

	private final FechaJson fechamento = new FechaJson(this);
	
	public AbreJson() {
		super("<AbreJson>");
	}
	
	@Override
	public String getS() {
		return "{ ";
	}
	
}