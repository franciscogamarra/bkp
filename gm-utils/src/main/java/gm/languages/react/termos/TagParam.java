package gm.languages.react.termos;

import gm.languages.palavras.Palavra;

public class TagParam {
	public String name;
	public Palavra value;
	
	@Override
	public String toString() {
		return " " + name + "={"+value.getS()+"}";
	}
	
}
