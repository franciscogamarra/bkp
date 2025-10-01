package gm.languages.react.termos;

import gm.languages.palavras.Palavra;

public class Assinatura extends Palavra {

	private String simpleName;

	public Assinatura(String simpleName) {
		super("");
		this.simpleName = simpleName;
	}
	
	@Override
	public String getS() {
		return "public class " + simpleName + " extends FC<"+simpleName+">";
	}

}
