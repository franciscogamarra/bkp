package gm.languages.palavras.comuns.conjuntos.parametro;

import gm.languages.palavras.Palavra;
import lombok.Getter;

@Getter
public class FechaParametro extends Palavra {

	private AbreParametro abertura;

	public FechaParametro(AbreParametro abertura) {
		super("");
		this.abertura = abertura;
	}
	
	@Override
	public String getS() {
		
		if (abertura.isVazio()) {
			return "";
		}
		
		return "}";
	}

	@Override
	public int getEspacos() {
		return 0;
	}
	
}
