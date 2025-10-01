package src.commom.utils.object;

import gm.utils.lambda.F0;

public class PrimeiroNaoNulo {

	@SafeVarargs
	public static <T> T get(F0<T>... calls) {
		for (F0<T> ft : calls) {
			T o = ft.call();
			if (!Null.is(o)) {
				return o;
			}
		}
		return null;
	}

}
