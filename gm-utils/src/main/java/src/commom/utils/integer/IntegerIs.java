package src.commom.utils.integer;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

public class IntegerIs {

	public static boolean is(Object o) {

		if (Null.is(o)) {
			return false;
		}

		if (Number.isInteger(o)) {
			return true;
		}

		String s = StringParse.get(o);
		s = StringTrim.plus(s);

		if (StringEmpty.is(s)) {
			return false;
		}

		if (s.endsWith(".")) {
			s = StringRight.ignore1(s);
		}

		if (s.startsWith("-")) {
			s = s.substring(1);
		}

		String n = StringExtraiNumeros.exec(s);

		if (!StringCompare.eq(s, n)) {
			return false;
		}

		int len = StringLength.get(s);

		if (len > 10) {
			return false;
		}
		if (len < 10) {
			return true;
		} else {
			return StringCompare.compare(s, "2147483647") < 1;
		}

	}
	
	@IgnorarDaquiPraBaixo
	
	private static class Number {
		
		private static boolean isInteger(Object o) {
			
			if (o == null) {
				return false;
			}
			
			if (o instanceof Integer) {
				return true;
			}
			
			String s = StringParse.get(o);
			
			if (StringEmpty.is(s)) {
				return false;
			}
			
			s = s.trim();
			
			if ("0".equals(s) || s.replace("0", "").isEmpty()) {
				return true;
			}
			
			try {
				Integer.parseInt(s);
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}
		
	}

}
