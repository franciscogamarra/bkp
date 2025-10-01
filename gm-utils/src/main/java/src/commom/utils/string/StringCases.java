package src.commom.utils.string;
import js.support.console;

public class StringCases {

	public static String kebab(String s) {
		return exec(s, "-");
	}

	public static String snake(String s) {
		return exec(s, "_");
	}

	public static String dot(String s) {
		return exec(s, ".");
	}

	public static String camel(String s) {
		return StringCamelCase.exec(s);
	}

	public static String exec(String s, String separator) {

		if (StringEmpty.is(s)) {
			return "";
		}

		String original = s;

		boolean fim = s.endsWith(" ");

		s = StringRemoveAcentos.exec(s);

		for (int i = 0; i < StringConstants.MAIUSCULAS.size(); i++) {
			String x = StringConstants.MAIUSCULAS.get(i);
			s = StringReplace.exec(s, x, " " + x);
		}

		s = StringTrim.plus(s);
		s = s.toLowerCase();

		if (fim) {
			s += " ";
		}

		s = StringReplace.exec(s, " ", separator);
		s = StringReplace.exec(s, separator+separator, separator);

		if (StringEmpty.is(s)) {
			console.log(original);
		}

		return s;

	}
	
	public static String upper(String s) {
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.toUpperCase();
	}
	
	public static String lower(String s) {
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.toLowerCase();
	}

}
