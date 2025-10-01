package src.commom.utils.string;

import src.commom.utils.integer.IntegerCompare;

public class StringAfterLast {

	private StringAfterLast() {}

	public static String get(String s, String substring) {
		int i = s.lastIndexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		return s.substring(i + substring.length());
	}

	public static String obrig(String s, String substring) {
		return StringObrig.get(get(s, substring));
	}

}
