package src.commom.utils.object;

import gm.utils.lambda.F0;

public class Obrig {

	public static <T> T check(T o) {
		return checkWithMessage(o, "Obrig.check: o is null");
	}

	public static <T> T checkWithMessage(T o, String message) {
		if (Null.is(o)) {
			throw new RuntimeException(message);
		}
		return o;
	}

	public static <T> T checkWithDynamicMessage(T o, F0<String> getMessage) {
		if (Null.is(o)) {
			throw new RuntimeException(getMessage.call());
		}
		return o;
	}

}
