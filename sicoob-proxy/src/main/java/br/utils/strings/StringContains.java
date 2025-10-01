package br.utils.strings;

public class StringContains {
	
	private StringContains() {
	}
	
	public static boolean is(String a, String b) {
		if (a == null || b == null) {
			return false;
		}
		if (b.length() == 0) {
			return true;
		}
		if (a.length() == 0) {
			return false;
		}
		return a.indexOf(b) > -1;
	}
	
}
