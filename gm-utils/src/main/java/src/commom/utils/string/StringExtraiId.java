package src.commom.utils.string;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.integer.IntegerIs;

public class StringExtraiId {

	@PodeSerNull
	public static String get(String s) {
		
		s = StringTrim.plusNull(s);
		
		if (s == null) {
			return null;
		}
		
		if (StringContains.is(s, "-")) {
			s = StringBeforeFirst.get(s, "-");
			if (s == null) {
				return null;
			} else {
				s = s.trim();
			}
		}
		
		if (IntegerIs.is(s)) {
			return s;
		} else {
			return null;
		}
		
	}

	@IgnorarDaquiPraBaixo
	
	private StringExtraiId() {}
	
}
