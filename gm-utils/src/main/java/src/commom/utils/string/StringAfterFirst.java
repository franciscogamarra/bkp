package src.commom.utils.string;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;

public class StringAfterFirst {

	@PodeSerNull
	public static String get(String s, String substring) {
		
		if (Null.is(s) || Null.is(substring)) {
			return null;
		}
		
		int i = s.indexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		i += substring.length();
		return s.substring(i);
	}

	public static String obrig(String s, String substring) {
		return StringObrig.get(get(s, substring));
	}

	@IgnorarDaquiPraBaixo
	
	private StringAfterFirst() {}

}
