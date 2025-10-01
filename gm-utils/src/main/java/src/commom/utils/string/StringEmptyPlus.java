package src.commom.utils.string;

public class StringEmptyPlus {

	private StringEmptyPlus() {}

	public static boolean is(String s) {

		if (StringEmpty.is(s)) {
			return true;
		}

		s = StringTrim.plus(s).toLowerCase();

		if (StringCompare.eq(s, "null")) {
			return true;
		}
		if (StringCompare.eq(s, "undefined")) {
			return true;
		} else {
			return false;
		}

	}

}
