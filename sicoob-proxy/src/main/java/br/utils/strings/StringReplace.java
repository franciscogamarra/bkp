package br.utils.strings;

import br.utils.lambda.F0;

public class StringReplace {
	
	public static String exec(String s, String a, String b) {
		
		if (s == null || a == null) {
			return "";
		}
		
		if (s.indexOf(a) == -1) {
			return s;
		}
		
		if (b == null) {
			b = "";
		}
		
		if (StringCompare.eq(s, a)) {
			return b;
		}
		
		return StringSplit.exec(s, a).joinString(b);
		
	}
	
	public static String whilee(String s, String a, String b) {
		
		if (s == null || a == null || a.contentEquals("")) {
			return "";
		}
		
		if (StringCompare.eq(a, b)) {
			return s;
		}
		
		if (b == null) {
			b = "";
		}

		int count = 0;
		while (s.indexOf(a) > -1) {
			String last = s;
			s = exec(s, a, b);
			if (StringCompare.eq(s, last)) {
				return s;
			}
			if (++count > 1000) {
				break;
			}				
		}
		
		return s;
		
	}
	
	public static String ifContains(String s, String a, F0<String> b) {
		if (StringContains.is(s, a)) {
			return exec(s, a, b.call());
		}
		return s;
	}
	
}
