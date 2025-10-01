package gm.languages.react.termos;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReactTagOpen extends Palavra {

	protected TipoJava tipo;
	private ReactTagOk ok;
	
	public ReactTagOpen(TipoJava tipo) {
		super("");
		this.tipo = tipo;
	}
	
	public void setTipo(TipoJava tipo) {
		this.tipo = tipo;
		atualizaTipo();
	}

	private void atualizaTipo() {
		if (ok != null) {
			if (tipo == null) {
				tipo = ok.tipo;
			} else {
				ok.tipo = tipo;
			}
		}
	}
	
	public void setOk(ReactTagOk ok) {
		this.ok = ok;
		if (ok.getAbertura() != this) {
			ok.setAbertura(this);
		}
		atualizaTipo();
	}
	
	@Override
	public String getS() {
		String s = tipo.getS() + ".r()";
		if (ok == null) {
			s += "|?ReactTagOpen?|";
		}
		return s;
	}	

}
