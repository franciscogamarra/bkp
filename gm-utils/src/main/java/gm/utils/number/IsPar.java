package gm.utils.number;

import src.commom.utils.string.StringExtraiNumeros;

public class IsPar {

	public static boolean is(Object o) {
		
		if (o == null) {
			return false;
		}
		
		String s = o + "";
		s = StringExtraiNumeros.exec(s);
		
		for (int i = 0; i < 10; i++) {
			if (s.endsWith(""+i)) {
				return true;
			}
		}
		
		return false;
		
	}
	
}