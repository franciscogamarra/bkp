package src.commom.utils.string;

public class StringSafe {

	private StringSafe() {}

	public static String get(String s, String def) {
		if (StringEmpty.is(s)) {
			return def;
		}
		return s;
	}

}
