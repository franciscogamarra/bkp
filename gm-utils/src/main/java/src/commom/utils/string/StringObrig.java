package src.commom.utils.string;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class StringObrig {

	public static String get(String s) {
		return get2(s, "s em branco");
	}

	public static String get2(String s, String message) {
		if (StringEmpty.is(message)) {
			throw new Error("mensagem em branco");
		}
		if (StringEmpty.is(s)) {
			throw new Error(message);
		}
		return s;
	}

	@IgnorarDaquiPraBaixo
	
	private StringObrig() {}

}
