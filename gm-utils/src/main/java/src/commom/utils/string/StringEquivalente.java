package src.commom.utils.string;

public class StringEquivalente {

	private StringEquivalente() {}

	public static boolean is(String a, String b) {
		return (
			StringCompare.eqIgnoreCase(
				StringTrim.plus(StringRemoveAcentos.exec(a)),
				StringTrim.plus(StringRemoveAcentos.exec(b))
			)
		);
	}
	
	public static boolean ne(String a, String b) {
		return !is(a, b);
	}

}
