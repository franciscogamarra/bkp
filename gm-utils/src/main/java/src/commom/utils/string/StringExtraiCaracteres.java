package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class StringExtraiCaracteres {

	private StringExtraiCaracteres() {}

	public static String exec(String s, Itens<String> list) {

		if (StringEmpty.is(s)) {
			return "";
		}

		String s2 = "";

		while (!StringEmpty.is(s)) {
			String x = s.substring(0, 1);
			s = s.substring(1);
			if (list.contains(x)) {
				s2 += x;
			}
		}

		return s2;

	}

}
