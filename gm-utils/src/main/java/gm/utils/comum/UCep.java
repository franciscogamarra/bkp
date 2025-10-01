package gm.utils.comum;

import js.support.console;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;

public class UCep {

	public static String formatParcial(String s) {

		if (StringEmpty.is(s)) {
			return "";
		}

		s = StringExtraiNumeros.exec(s);

		if (StringEmpty.is(s)) {
			return "";
		}

		if (StringLength.get(s) < 3) {
			return s;
		}
		if (StringLength.get(s) > 8) {
			s = s.substring(0, 8);
		}

		String x = s.substring(0, 2) + ".";
		s = s.substring(2);

		if (StringLength.get(s) > 3) {
			x += s.substring(0, 3) + "-";
			s = s.substring(3);
		}

		return x + s;

	}

	public static void main(String[] args) {
		//console.log(s);(formatParcial(""));
		//console.log(s);(formatParcial("7"));
		//console.log(s);(formatParcial("72"));
		//console.log(s);(formatParcial("723"));
		//console.log(s);(formatParcial("7231"));
		//console.log(s);(formatParcial("72318"));
		//console.log(s);(formatParcial("723180"));
		//console.log(s);(formatParcial("7231802"));
		//console.log(s);(formatParcial("72318024"));
		console.log(formatParcial("723180240"));
	}

	public static boolean isValid(String s) {
		s = StringExtraiNumeros.exec(s);
		if (!StringLength.is(s, 8)) {
			return false;
		}
		return true;
	}

	public static String getBairro(String s) {
		return "Um Bairro Mock para o cep " + s;
	}

	public static String mock(int i) {
		return "72.318-" + IntegerFormat.zerosEsquerda(i, 3);
	}

}
