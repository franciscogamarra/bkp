package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.ts.expressoes.ClassTs;
import gm.languages.ts.expressoes.TipoTs;
import gm.languages.ts.javaToTs.annotacoes.Interface;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.reflection.Metodo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvocacaoDeUmMetodo extends Palavra {

	private Palavra newTipo;
	private Metodo metodo;
	private Lst<Palavra> inicioParametros;
	
	public void setMetodo(Metodo metodo) {
		this.metodo = metodo;
	}
	
	public InvocacaoDeUmMetodo(Object s) {
		super(s);
	}
	
	public int getParametrosCount() {
		return inicioParametros.size();
	}
	
	@Override
	public String getS() {
		
		if (newTipo == null) {
			return super.getS();
		}
		
		if (newTipo instanceof TipoTs) {
			TipoTs tipo = (TipoTs) newTipo;
			ClassTs o = tipo.getClasse();
			
			if (o.getPath() == null && o.getSimpleName().contentEquals("Object") || o.getSimpleName().contentEquals("any")) {
				return "{}";
			}
			
			Class<?> classe = UClass.getClass(o.getPath() + "." + o.getSimpleName());
			if (classe != null && classe.isAnnotationPresent(Interface.class)) {
				return "{} as " + newTipo.getS();
			}
		}
		
		return "new " + newTipo.getS();
		
	}
	
	public TipoJava getTipoNewTipoJava() {

		if (newTipo instanceof TipoJava) {
			return (TipoJava) newTipo;
		}

		throw new NaoImplementadoException();
		
	}
	
	public TipoTs getTipoNewTipo() {
		
		if (newTipo instanceof TipoTs) {
			return (TipoTs) newTipo;
		}

		if (newTipo instanceof TipoJava) {
			TipoJava tp = (TipoJava) newTipo;
			return TipoTs.build(tp);
		}
		
		throw new NaoImplementadoException();
		
	}

}
