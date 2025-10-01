package src.commom.utils.string;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class StringRight {

	public static String ignore1(String s) {
		return ignore(s, 1);
	}

	public static String ignore(String s, int count) {

		if (StringEmpty.is(s)) {
			return "";
		}

		int len = StringLength.get(s);

		if (len <= count) {
			return "";
		}
		return s.substring(0, len - count);

	}

	public static String get(String s, int count) {

		if (StringEmpty.is(s)) {
			return "";
		}
		
		int len = StringLength.get(s);
		
		if (len < count) {
			return s;
		}
		
		return s.substring(len - count);
		
	}

	@IgnorarDaquiPraBaixo
	
	private StringRight() {}
	
}
