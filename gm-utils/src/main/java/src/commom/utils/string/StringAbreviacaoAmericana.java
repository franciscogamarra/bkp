package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class StringAbreviacaoAmericana {

	private static final Itens<String> LETRAS = Itens.build(
		"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","z"
	);

	public static String exec(String s) {

		s = StringTrim.plus(s);

		if (StringLength.get(s) < 3) {
			return s;
		}

		s = s.toLowerCase();

		s = StringRemoveAcentos.exec(s);
		String before = s.substring(0, 1);
		String after = StringRight.get(s, 1);

		s = s.substring(1);
		s = StringRight.ignore1(s);

		s = StringExtraiCaracteres.exec(s, LETRAS);

		return before + s + after;

	}

}
