package src.commom.utils.string;

import js.Js;
import src.commom.utils.object.Null;

public class StringIs {

	private StringIs() {}

	public static boolean is(Object o) {
		if (Null.is(o)) {
			return false;
		}
		return StringCompare.eq(Js.typeof(o), "string");
	}

}
