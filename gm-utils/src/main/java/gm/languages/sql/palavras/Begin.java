package gm.languages.sql.palavras;

import gm.languages.palavras.PalavraReservada;
import lombok.Getter;

@Getter
public class Begin extends PalavraReservada {
	
	private End fechamento;
	
	public void setFechamento(End fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
}