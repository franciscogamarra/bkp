package src.commom.utils.string;

import src.commom.utils.integer.IntegerCompare;

public class StringBeforeLast {

	private StringBeforeLast() {}

	public static String get(String s, String substring) {
		int i = s.lastIndexOf(substring);
		if (IntegerCompare.eq(i, -1)) {
			return null;
		}
		return s.substring(0, i);
	}

	public static String obrig(String s, String substring) {
		return StringObrig.get(get(s, substring));
	}

}
