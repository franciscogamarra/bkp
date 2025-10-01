package src.commom.utils.string;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import js.Js;
import src.commom.utils.object.Null;

public class StringTrim {

	private StringTrim() {}

	public static String trim(String s) {
		if (Null.is(s)) {
			return "";
		}
		return s.trim();
	}

	public static String plus(String s) {

		if (Null.is(s)) {
			return "";
		}

		if (Js.inJava) {

			String x = "";

			for (int i = 0; i < s.length(); i++) {

				char c = s.charAt(i);

				if (c == 160) {
					x += " ";
				} else {
					x += c;
				}

			}

			s = x;

		}

		s = trim(s);

		s = StringReplace.exec(s, "\t", " ");
		s = StringReplace.exec(s, "\n", " ");
		s = StringReplace.exec(s, "\r", " ");
		s = StringReplace.whilee(s, "  ", " ");

		return trim(s);

	}

	@PodeSerNull
	public static String plusNull(String s) {
		s = plus(s);
		return StringEmpty.is(s) ? null : s;
	}

	public static String left(String s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.startsWith(" ") || s.startsWith("\t") || s.startsWith("\n")) {
			s = s.substring(1);
		}
		return s;
	}

	public static String right(String s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.endsWith(" ") || s.endsWith("\t") || s.endsWith("\n")) {
			s = StringRight.ignore1(s);
		}
		return s;
	}
	
	public static String plusInputando(String s) {

		if (StringEmpty.is(s)) {
			return "";
		}
		
		boolean espaco = s.endsWith(" ");

		s = plus(s);
		
		if (espaco) {
			s += " ";
		}
		
		return s;
		
		
	}

}
