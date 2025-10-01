package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.sql.palavras.Case;
import gm.languages.sql.palavras.End;
import lombok.Getter;

@Getter
public class EndCase extends Palavra {

	private Case abertura;
	private End end;

	public EndCase(Case cs, End end) {
		super(end.getS());
		this.abertura = cs;
		this.end = end;
		this.abertura.setFechamento(this);
	}
	
	@Override
	public String toString() {
		if (emAnalise) {
			return "<End of "+abertura+">";
		}
		return end.toString();
	}

}
