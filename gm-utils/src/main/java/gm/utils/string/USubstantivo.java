package gm.utils.string;

import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringTrim;

public class USubstantivo {
	public static boolean masculino(String s) {
		s = StringBeforeFirst.get(StringTrim.plus(s) + " ", " ").toLowerCase();
		if (s.endsWith("o")) {
			return true;
		}
		if ("uf".equals(s)) {
		}
		return false;
	}
}