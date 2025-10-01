package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.palavras.Parenteses;
import gm.languages.palavras.comuns.simples.AbreParenteses;

public class Funcao extends Palavra {
	
	private final Parenteses params = new Parenteses();
	private Palavra schema;
	private Palavra banco;

	public Funcao(Palavra banco, Palavra schema, Palavra nome) {
		super(nome.getS());
		this.banco = banco;
		this.schema = schema;
	}
	
	public void extraiParams(AbreParenteses abre, Palavras itens) {
		params.extrai(abre, itens);
	}
	
	@Override
	public String toString() {
		
		String s = getS();
		
		if (schema != null) {
			s = schema.getS() + "." + s;
			if (banco != null) {
				s = banco.getS() + "." + s;
			}
		}
		
//		s += "(" + params.toString(", ") + ")";
		
		return toStringFinal(s);
		
	}
	
	@Override
	public Palavras getItens() {
		return params.getItens();
	}

}
