package gm.languages.react.termos;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReactTagOk extends Palavra {

	protected TipoJava tipo;
	private ReactTagOpen abertura;
	private ReactTagClose fechamento;
	
	public ReactTagOk() {
		super(".ok(");
	}

	public ReactTagOk(TipoJava tipo) {
		this();
		setTipo(tipo);
	}
	
	public void setTipo(TipoJava tipo) {
		this.tipo = tipo;
		atualizaTipo();
	}
	
	private void atualizaTipo() {
		if (abertura != null) {
			if (tipo == null) {
				tipo = abertura.tipo;
			} else {
				abertura.tipo = tipo;
			}
		}
	}
	
	@Override
	public String getS() {
		
		String s = ".ok(";
		
		if (abertura == null) {
			s = "|?ReactTagOk?|" + s;
		}
		
		if (fechamento == null) {
			s += "|?ReactTagOk?|";
		}
		
		return s;
	}
	
	public void setAbertura(ReactTagOpen abertura) {
		this.abertura = abertura;
		if (abertura.getOk() != this) {
			abertura.setOk(this);
		}
		atualizaTipo();
	}
	
	public void setFechamento(ReactTagClose fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getOk() != this) {
			fechamento.setOk(this);
		}
	}
	
	
}
