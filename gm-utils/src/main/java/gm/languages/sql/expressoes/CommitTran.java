package gm.languages.sql.expressoes;

import gm.languages.palavras.PalavraReservada;
import gm.languages.sql.palavras.Commit;
import gm.languages.sql.palavras.Tran;
import lombok.Getter;

@Getter
public class CommitTran extends PalavraReservada {
	
	private BeginTran abertura;
	
	public CommitTran(Commit commit, Tran tran) {
		super(commit.getS() + " " + tran.getS());
	}

	public void setAbertura(BeginTran abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
}