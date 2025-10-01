package src.commom.utils.string;

import gm.utils.lambda.F0;
import src.commom.utils.object.Null;

public class StringReplace {

	public static String exec(String s, String a, String b) {
		
		if (Null.is(s) || Null.is(a)) {
			return "";
		}

		if (s.indexOf(a) == -1) {
			return s;
		}

		if (Null.is(b)) {
			b = "";
		}
		
		if (StringCompare.eq(s, a)) {
			return b;
		}
		
		return StringSplit.exec(s, a).joinString(b);
		
	}
	
	public static String whilee(String s, String a, String b) {
		
		if (Null.is(s) || Null.is(a)) {
			return "";
		}

		if (Null.is(b)) {
			b = "";
		}

		while (s.indexOf(a) > -1) {
			String last = s;
			s = exec(s, a, b);
			if (StringCompare.eq(s, last)) {
				return s;
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
