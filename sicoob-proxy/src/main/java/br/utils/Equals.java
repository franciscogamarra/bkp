package br.utils;

import br.utils.strings.StringCompare;

public class Equals {

	public static boolean is(Object a, Object b) {
		
		if (a == b) {
			return true;
		}
		
		if (a == null || b == null) {
			return false;
		}
		
		if (a.equals(b)) {
			return true;
		}
		
		if (a.getClass() != b.getClass()) {
			return false;
		}
		
		if (a instanceof String) {
			return StringCompare.eq((String) a, (String) b);
		}
		
		return false;
		
	}
	
}
