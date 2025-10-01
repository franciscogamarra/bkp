package gm.languages.sql.expressoes;

import gm.languages.palavras.PalavraReservada;
import gm.languages.sql.palavras.Begin;
import gm.languages.sql.palavras.Tran;
import lombok.Getter;

@Getter
public class BeginTran extends PalavraReservada {
	
	private CommitTran fechamento;
	
	public BeginTran(Begin begin, Tran tran) {
		super(begin.getS() + " " + tran.getS());
	}
	
	public void setFechamento(CommitTran fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
}