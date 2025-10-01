package br.utils.strings;

import br.utils.ints.IntegerCompare;

public class StringBeforeFirst {
	
	public static String get(String s, String substring) {
		
		if (s == null || substring == null) {
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
		if (s == null) {
			return "";
		}
		return s;
	}
	
	private StringBeforeFirst() {}
	
}
