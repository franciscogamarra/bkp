package br.utils.strings;

import br.utils.ints.IntegerCompare;

public class StringAfterFirst {

	public static String get(String s, String substring) {

		if (s == null || substring == null) {
			return null;
		}

		int i = s.indexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		i += substring.length();
		return s.substring(i);
	}

	private StringAfterFirst() {}

}
