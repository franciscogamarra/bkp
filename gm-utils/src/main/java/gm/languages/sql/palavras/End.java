package gm.languages.sql.palavras;

import gm.languages.palavras.PalavraReservada;
import lombok.Getter;

@Getter
public class End extends PalavraReservada {
	
	private Begin abertura;

	public void setAbertura(Begin abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}