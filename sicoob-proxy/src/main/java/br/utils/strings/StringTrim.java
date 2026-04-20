package br.utils.strings;

import br.support.lambda.P1;

public class StringTrim {
	
	private StringTrim() {
	}
	
	public static String trim(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}
	
	public static String plus(String s) {
		
		if (s == null) {
			return "";
		}
		
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
		
		s = trim(s);
		
		s = StringReplace.exec(s, "\t", " ");
		s = StringReplace.exec(s, "  ", " ");
		s = StringReplace.exec(s, "\r", " ");
		s = StringReplace.exec(s, "\n ", "\n");
		s = StringReplace.whilee(s, "\n\n", "\n");
		s = StringReplace.whilee(s, "  ", " ");
		
		return trim(s);
		
	}
	
	public static String plusNull(String s) {
		s = plus(s);
		return StringEmpty.is(s) ? null : s;
	}
	
	public static String left(String s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.startsWith(" ") || s.startsWith("\t") || s.startsWith(" ")) {
			s = s.substring(1);
		}
		return s;
	}
	
	public static String right(String s) {
		if (StringEmpty.is(s)) {
			return "";
		}
		while (s.endsWith(" ") || s.endsWith("\t") || s.endsWith(" ")) {
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
	
	public static String plusNullTexto(String s) {
		
		s = plusNull(s);
		
		if (s == null) {
			return null;
		}
		
		s = s.replace("\u00ef", "");//i com trema
		s = s.replace("\u00bf", "");//interrogacao de cabeça para baixo
		s = s.replace(" !", "!");
		s = s.replace(" .", ".");
		
		return plusNull(s);
		
	}
	
	public static String plusHtml(String s) {
	
		s = plus(s);
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		if (s.startsWith("<br>")) {
			return plusHtml(s.substring(4));
		}
		
		if (s.endsWith("<br>")) {
			return plusHtml(StringRight.ignore(s, 4));
		}

		if (s.startsWith("&nbsp;")) {
			return plusHtml(s.substring(6));
		}

		if (s.endsWith("&nbsp;")) {
			return plusHtml(StringRight.ignore(s, 6));
		}

		while (s.contains("<br><br><br>")) {
			s = s.replace("<br><br><br>", "<br><br>");
		}
		
		return s;
		
	}
	
	public static void ifThen(String s, P1<String> then) {
		s = StringTrim.plusNull(s);
		if (s != null) {
			then.call(s);
		}
	}
	
}
