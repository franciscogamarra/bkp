package src.commom.utils.casewhen;

import gm.utils.lambda.F0;

public class When<T> {

	F0<Boolean> e;
	F0<T> r;
	When(F0<Boolean> e, F0<T> r) {
		this.e = e;
		this.r = r;
	}

}
