package src.commom.utils.string;

import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;

public class StringLength {

	private StringLength() {}

	public static int get(String s) {
		if (Null.is(s)) {
			return 0;
		}
		return s.length();
	}

	public static boolean is(String s, int size) {
		return IntegerCompare.eq(get(s), size);
	}

	public static String max(String s, int max) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		if (StringLength.get(s) > max) {
			return s.substring(0, max);
		}
		
		return s;
		
	}

}
