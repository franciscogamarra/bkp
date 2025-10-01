package src.commom.utils.object;

import gm.utils.lambda.F0;
import gm.utils.lambda.F1;

public class Safe {

	public static <OUT,IN> OUT get(IN o, F1<IN,OUT> getter, F0<OUT> other) {
		if (Null.is(o)) {
			return other.call();
		}
		return getter.call(o);
	}

	public static <OUT,IN> OUT safeNullPointer(IN o, F1<IN,OUT> getter, F0<OUT> other) {
		if (Null.is(o)) {
			return other.call();
		}
		try {
			return getter.call(o);
		} catch (Exception e) {
			return other.call();
		}
	}

}
