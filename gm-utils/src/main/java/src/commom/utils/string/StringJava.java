package src.commom.utils.string;

public class StringJava {

	private StringJava() {}

	public static String exec(String s, boolean primeiraMaiuscula) {

		if (StringEmpty.is(s)) {
			return null;
		}

		s = StringExtraiCaracteres.exec(s, StringConstants.JAVA_LETTERS);

		if (StringEmpty.is(s)) {
			return null;
		}

		if (primeiraMaiuscula) {
			s = StringPrimeiraMaiuscula.exec(s);
		} else {
			s = StringPrimeiraMinuscula.exec(s);
		}

		return s;

	}

	public static boolean isValid(String s, boolean primeiraMaiuscula) {

		if (StringEmpty.is(s)) {
			return false;
		}

		String x = exec(s, primeiraMaiuscula);

		return StringCompare.eq(s, x);

	}

}
