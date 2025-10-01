package src.commom.utils.string;

import src.commom.utils.comum.Box;

public class StringReplacePalavra {

	private StringReplacePalavra() {}

	private static String palavraInterno(String de, String expressao, String resultado) {

		if (StringEmpty.is(de)) {
			return "";
		}

		de = " " + de + " ";

		//nao usar StringBox pois dá referencia circular
		Box<String> box = new Box<>();
		box.set(de);

		StringConstants.SIMBOLOS.forEach(a -> {
			StringConstants.SIMBOLOS.forEach(b -> {
				String c = a + expressao + b;
				String d = a + resultado + b;
				box.set(StringReplace.exec(box.get(), c, d));
			});
		});

		de = box.get();
		de = de.substring(1);
		return de.substring(0, de.length() - 1);

	}

	public static String exec(String de, String expressao, String resultado) {

		if (!StringContains.is(de, expressao)) {
			return de;
		}

		if (StringContains.is(resultado, expressao)) {
			
			String x = "a";
			
			while (StringContains.is(resultado, x)) {
				x += "a";
			}
			
			de = palavraInterno(de, expressao, x);
			expressao = x;
			
		}

		return palavraInterno(de, expressao, resultado);

	}

}
