package src.commom.utils.string;

public class StringRepete {
	public static String exec(String s, int vezes) {
		String result = "";
		while (vezes > 0) {
			result += s;
			vezes--;
		}
		return result;
	}
}