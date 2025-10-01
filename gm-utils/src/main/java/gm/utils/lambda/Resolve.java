package gm.utils.lambda;

public class Resolve {

	public static <T> T ft(F0<T> func) {
		return func.call();
	}
	
}
