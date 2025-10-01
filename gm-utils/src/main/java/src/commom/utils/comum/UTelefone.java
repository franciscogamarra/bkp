package src.commom.utils.comum;

import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringRight;

public class UTelefone {

	public static final int MAX_LENGTH = 20;

	public static String formatParcial(String s, int numeros) {

		if (StringEmpty.is(s)) {
			return "";
		}

		s = StringExtraiNumeros.exec(s);

		if (StringEmpty.is(s)) {
			return "";
		}

		if (StringLength.get(s) < 2) {
			return "(" + s;
		}

		String x = "(" + s.substring(0, 2);
		s = s.substring(2);

		if (StringEmpty.is(s)) {
			return x;
		}

		s = StringLength.max(s, numeros);

		x += ") ";

		return x + formatNumero(s);

	}

	private static String formatNumero(String s) {
		s = StringExtraiNumeros.exec(s);
		if (StringEmpty.is(s)) {
			return "";
		}
		String x = "";
		if (StringLength.get(s) > 4) {
			while (StringLength.get(s) > 4) {
				x = "-" + StringRight.get(s, 4) + x;
				s = StringRight.ignore(s, 4);
			}
		}
		return s + x;
	}

	public static boolean isValid(String s, int len) {
		s = formatParcial(s, len);
		if (StringLength.get(s) < 14) {
			return false;
		}
		return true;
	}

	public static String format(Integer ddd, String numero) {
		String s = "";
		if (ddd != null) {
			s += "(" + ddd + ") ";
		}
		s += formatNumero(numero);
		return s.trim();
	}

	public static String aleatorio() {
		return formatParcial("619" + Randomico.getIntString(8), 9);
	}
	
	public static boolean isCelular(String s, boolean ddi, boolean ddd) {

		s = StringExtraiNumeros.exec(s);

		int len = StringLength.get(s);

		if (len == 0) {
			return false;
		}

		if (ddi) {
			if (len < 3) {
				return false;
			}
			s = s.substring(2);
			len -= 2;
		}

		if (ddd) {
			if (len < 3) {
				return false;
			}
			s = s.substring(2);
			len -= 2;
		}

		s = s.substring(0, 1);

		Integer i = IntegerParse.toInt(s);

		if (i < 8) {
			return false;
		}

		return true;

	}
	
	public static String formatComDdi(Integer ddi, Integer ddd, String numero) {
		String s = "";
		if (!Null.is(ddi)) {
			s = "+" + ddi + " ";
		}
		return s + formatComDdd(ddd, numero);
	}

	public static String formatComDdd(Integer ddd, String numero) {
		String s = "";
		if (!Null.is(ddd)) {
			s += "(" + ddd + ") ";
		}
		s += formatNumero(numero);
		return s.trim();
	}

}
