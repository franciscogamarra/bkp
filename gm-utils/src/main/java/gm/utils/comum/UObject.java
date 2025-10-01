package gm.utils.comum;

import src.commom.utils.string.StringEmpty;

public class UObject {

	public static boolean isEmpty(Object o) {
		return o == null || StringEmpty.is(o.toString());
	}

	@SafeVarargs
	public static <T> T coalesce(T... list) {
		for (T s : list) {
			if (!isEmpty(s)) {
				return s;
			}
		}
		return null;
	}

}
