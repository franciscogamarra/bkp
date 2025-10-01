package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class StringReverse {

	public static String get(String s) {
		Itens<String> list = StringSplit.exec(s, "");
		list.inverteOrdem();
		return list.concatStrings(i -> i, "");
	}

}
