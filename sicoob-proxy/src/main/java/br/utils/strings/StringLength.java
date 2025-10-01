package br.utils.strings;

import br.utils.DevException;

public class StringLength {
	
	public static int get(String s) {
		return s == null ? 0 : s.length();
	}
	
	public static void assertMax(String s, int max) {
		int len = get(s);
		if (len > max) {
			throw DevException.build("length > " + max);
		}
	}
	
	public static String max(String s, int max) {
		int len = get(s);
		if (len > max) {
			s = s.substring(0, max);
		}
		return s;
	}
	
}