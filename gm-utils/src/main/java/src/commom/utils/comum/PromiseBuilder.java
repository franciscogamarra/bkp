package src.commom.utils.comum;

import gm.utils.lambda.F0;
import gm.utils.lambda.P1;
import js.Js;
import js.promise.Promise;

public class PromiseBuilder {

	public static <T> void resolver(F0<T> func, P1<T> resolve, P1<Throwable> reject) {
		try {
			resolve.call(func.call());
		} catch (Exception e) {
//			e.printStackTrace();
			reject.call(e);
		}
	}
	
	public static <T> Promise<T> ft(F0<T> func) {
		return new Promise<>((/*P1<?>*/ resolve, /*P1<?>*/ reject) -> resolver(func, resolve, reject));
	}

	public static <T> Promise<T> withDelay(F0<T> func, int mills) {
		if (mills < 1) {
			return ft(func);
		} else {
			return new Promise<>((/*P1<?>*/ resolve, /*P1<?>*/ reject) -> Js.setTimeout(() -> resolver(func, resolve, reject), mills));
		}
	}

}