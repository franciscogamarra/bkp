package src.commom.utils.string;

public class StringPrimeiraMinuscula {

	private StringPrimeiraMinuscula() {}

	public static String exec(String s) {
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

}
