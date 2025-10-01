package src.commom.utils.string;

import gm.languages.ts.javaToTs.JS;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;

public class StringSplit extends JS {

	public static Itens<String> exec(String s, String sub) {
		
		Itens<String> itens = new Itens<>();
		
		if (Null.is(s)) {
			return itens;
		}
		
		if (Null.is(sub)) {
			sub = "";
		}
		
		itens.addArray(split(s, sub));
		
		return itens;
		
	}

	@IgnorarDaquiPraBaixo
	
	private StringSplit() {}
	
	public static void main(String[] args) {
		exec("/v1/clients/","/").print();
	}
	
}
