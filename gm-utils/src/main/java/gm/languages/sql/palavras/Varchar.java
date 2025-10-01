package gm.languages.sql.palavras;

import gm.languages.palavras.comuns.literal.InteiroLiteral;
import gm.languages.sql.expressoes.TipoSql;
import lombok.Setter;

@Setter
public class Varchar extends TipoSql {
	
	private Integer size;
	private boolean max;
	
	public void setSize(InteiroLiteral size) {
		this.size = size.toInt();
	}
	
	@Override
	public String toString() {
		String s = getS() + "("+(max ? "max" : size)+")";
		return toStringFinal(s);
	}
	
}