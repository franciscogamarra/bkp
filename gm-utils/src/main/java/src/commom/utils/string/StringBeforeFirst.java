package src.commom.utils.string;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;

public class StringBeforeFirst {

	@PodeSerNull
	public static String get(String s, String substring) {
		
		if (Null.is(s) || Null.is(substring)) {
			return null;
		}
		
		int i = s.indexOf(substring);
		
		if (IntegerCompare.eq(i, -1)) {
			return "";
		}
		
		if (i == 0) {
			return "";
		}
		
		return s.substring(0, i);
		
	}

	public static String safe(String s, String substring) {
		s = get(s, substring);
		if (Null.is(s)) {
			return "";
		}
		return s;
	}

	public static String obrig(String s, String substring) {
		return StringObrig.get(get(s, substring));
	}

	@IgnorarDaquiPraBaixo
	
	private StringBeforeFirst() {}
	
}
