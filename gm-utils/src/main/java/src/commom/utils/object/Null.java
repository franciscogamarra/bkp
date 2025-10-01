package src.commom.utils.object;

import js.Js;

public class Null {

	public static boolean is(Object o) {
		return o == null || o == Js.undefined;
	}

	public static boolean isEmpty(Object o) {
		return is(o) || o == "";
	}

}
