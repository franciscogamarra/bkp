package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReactTagParamOpen extends Palavra {

	private ReactTagParamClose fechamento;
	private ReactTagOpen tag;
	public final String name;
	
	public ReactTagParamOpen(ReactTagOpen tag, String name) {
		super("");
		this.tag = tag;
		this.name = name;
	}
	
	public void setFechamento(ReactTagParamClose fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
	@Override
	public String getS() {
		return "." + name + "(";
	}
	
	@Override
	public int getEspacos() {
		return 0;
	}

}
