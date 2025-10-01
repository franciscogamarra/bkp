package src.commom.utils.string;

public class StringExtraiNumeros {

	private StringExtraiNumeros() {}
	
	private static final String NUMEROS = "0123456789";

	public static String exec(String s) {
		return StringExtraiCaracteresBaseadoEmString.exec(s, NUMEROS);
	}

	public static String nullIfEmpty(String s) {
		return StringExtraiCaracteresBaseadoEmString.nullIfEmpty(s, NUMEROS);
	}
	
}
