package gm.utils.number;

import src.commom.utils.integer.IntegerIs;
import src.commom.utils.string.StringEmpty;

public class UInteger {

	public static boolean isLongInt(String s) {
		if (StringEmpty.is(s)) {
			return false;
		}
		s = s.trim();
		while (!s.isEmpty()) {
			if (IntegerIs.is(s)) {
				return true;
			}
			String x = s.substring(0, 1);
			if (!IntegerIs.is(x)) {
				return false;
			}
			s = s.substring(1);
		}
		return false;
	}

}
