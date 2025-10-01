package src.commom.utils.doubles;

import src.commom.utils.object.Null;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiCaracteresBaseadoEmString;
import src.commom.utils.string.StringOcorrencias;
import src.commom.utils.string.StringParse;

public class DoubleIs {
	
	private static final String NUMEROS = "0123456789.";

	public static boolean is(Object o) {

		if (Null.is(o)) {
			return false;
		}
		
		String s = StringParse.get(o);

		if (StringEmpty.is(s)) {
			return false;
		}

		if (!StringCompare.eq(s, StringExtraiCaracteresBaseadoEmString.exec(s, NUMEROS))) {
			return false;
		}
		
		return StringOcorrencias.get(s, ".") < 2;

	}

}
