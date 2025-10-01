package src.commom.utils.string;

import src.commom.utils.object.Null;

public class StringContains {

	private StringContains() {}

	public static boolean is(String a, String b) {
		if (Null.is(a) || Null.is(b)) {
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
