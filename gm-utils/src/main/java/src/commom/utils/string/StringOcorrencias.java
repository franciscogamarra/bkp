package src.commom.utils.string;

public class StringOcorrencias {

	private StringOcorrencias() {}

	public static int get(String s, String substring) {
		int i = s.length();
		s = s.replace(substring, "");
		i -= s.length();
		return i / substring.length();
	}

}
