package gm.languages.sql.palavras;

import gm.languages.palavras.comuns.literal.InteiroLiteral;
import gm.languages.sql.expressoes.TipoSql;

public class Numeric extends TipoSql {
	
	private Integer left;
	private Integer rigth;
	
	public void set(InteiroLiteral left, InteiroLiteral rigth) {
		this.left = left.toInt();
		this.rigth = rigth.toInt();
	}
	
	@Override
	public String toString() {
		String s = getS() + "("+left+","+rigth+")";
		return toStringFinal(s);
	}
	
}