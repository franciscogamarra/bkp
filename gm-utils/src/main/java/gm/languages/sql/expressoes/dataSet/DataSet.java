package gm.languages.sql.expressoes.dataSet;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.sql.expressoes.Funcao;
import gm.languages.sql.expressoes.cte.CteReference;
import gm.languages.sql.expressoes.variaveis.VarReference;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataSet extends Palavra {

	private Palavra banco;
	private Palavra schema;
	private String alias;
	private Palavra nome;
	private boolean nolock;

	public DataSet(Palavra nome) {
		super("");
		this.nome = nome;
	}

	public DataSet(Palavra schema, NaoClassificada nome) {
		this(nome);
		this.schema = schema;
	}
	
	public DataSet(Palavra banco, Palavra schema, NaoClassificada nome) {
		this(schema, nome);
		this.banco = banco;
	}
	

	@Override
	public String toString() {

		String s;

		if (isTable()) {
			s = nome.getS();
		} else {
			s = nome.toString();
		}

		if (schema != null) {
			s = schema.getS() + "." + s;
			if (banco != null) {
				s = banco.getS() + "." + s;
			}
		}

		if (alias != null) {
			s += " " + alias;
		}
		
		if (nolock) {
			s += " with(nolock)";
		} else if (faltaWithNoLock()) {
//			s += " /* with(nolock) */";
//			s += " with(nolock)";
			s += " with(nolock) /*gm*/";
		}

		return toStringFinal(s);

	}

	public boolean isTable() {
		return !isVariavel() && !isFunction() && !isCte();
	}

	public boolean isVariavel() {
		return nome instanceof VarReference;
	}
	
	public boolean isFunction() {
		return nome instanceof Funcao;
	}
	
	public boolean isCte() {
		return nome instanceof CteReference;
	}
	
	public boolean faltaWithNoLock() {
		return isTable() && !nolock;
	}

	public Funcao marcaComoFuncao() {
		if (!(nome instanceof Funcao)) {
			nome = new Funcao(banco, schema, nome);
			setSchema(null);
		}
		return (Funcao) nome;
	}
	
	public boolean ehReferencia(String s) {
		
		if (alias == null) {
			
			if (s.equalsIgnoreCase(nome.getS())) {
				return true;
			}
			
			if (schema == null) {
				return false;
			}
			
			String ss = schema.getS() + "." + nome.getS();
			
			if (s.equalsIgnoreCase(ss)) {
				return true;
			}
			
			if (banco == null) {
				return false;
			}
			
			ss = banco.getS() + "." + ss;
			
			return ss.equalsIgnoreCase(s);
			
		} else {
			return alias.equalsIgnoreCase(s);
		}
		
	}

	public String getAliasOuNome() {
		return alias == null ? nome.getS() : alias;
	}
	
	@Override
	public Palavras getItens() {
		
		if (isFunction()) {
			Funcao funcao = (Funcao) nome;
			return funcao.getItens();
		}
		
		return null;
		
	}

}