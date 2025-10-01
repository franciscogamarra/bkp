package src.commom.utils.string;

public class StringContainsPlus {

	private StringContainsPlus() {}

	public static boolean is(String a, String b) {
		a = StringRemoveAcentos.exec(a.toLowerCase());
		b = StringRemoveAcentos.exec(b.toLowerCase());
		return StringContains.is(a,b);
	}
	
}
