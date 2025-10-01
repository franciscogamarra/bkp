package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import gm.utils.reflection.Atributo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvocacaoDeUmAtributo extends Palavra {

	private Atributo atributo;
	
	public InvocacaoDeUmAtributo(Object s) {
		super(s);
	}
	
	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}

}
