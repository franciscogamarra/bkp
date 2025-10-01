package src.commom.utils.numeric;

import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiCaracteres;
import src.commom.utils.string.StringLength;

public class NumericCompare {

	private static final Itens<String> CHARS = Itens.build("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",");

	private static String prepare(String s) {

		s = StringExtraiCaracteres.exec(s, CHARS);

		if (StringEmpty.is(s)) {
			return "0,0";
		}
		if (!StringContains.is(s, ",")) {
			s += ",0";
		} else if (s.endsWith(",")) {
			s += "0";
		}

		if (s.startsWith(",")) {
			s = "0" + s;
		}

		return s;

	}

	public static int compare(String a, String b) {

		a = prepare(a);
		b = prepare(b);

		if (StringCompare.eq(a, b)) {
			return 0;
		}

		int int_a = IntegerParse.toInt(StringBeforeFirst.get(a, ","));
		int int_b = IntegerParse.toInt(StringBeforeFirst.get(b, ","));

		if (int_a < int_b) {
			return -1;
		}
		if (int_a > int_b) {
			return 1;
		}

		a = StringAfterFirst.get(a, ",");
		b = StringAfterFirst.get(b, ",");

		int len_a = StringLength.get(a);
		int len_b = StringLength.get(b);

		if (len_a > len_b) {
			while (len_a > len_b) {
				b += "0";
				len_b++;
			}
		} else if (len_a < len_b) {
			while (len_a < len_b) {
				a += "0";
				len_a++;
			}
		}

		int_a = IntegerParse.toInt(a);
		int_b = IntegerParse.toInt(b);

		if (int_a < int_b) {
			return -1;
		}
		if (int_a > int_b) {
			return 1;
		} else {
			return 0;
		}

	}

	private static boolean compareIs(String a, String b, int value) {
		int x = compare(a, b);
		return IntegerCompare.eq(x, value);
	}

	public static boolean eq(String a, String b) {
		return compareIs(a, b, 0);
	}

	public static boolean ne(String a, String b) {
		return !eq(a,b);
	}

	public static boolean isZero(String s) {
		return eq(s, "0");
	}

	public static boolean maior(String a, String b) {
		return compareIs(a, b, 1);
	}

	public static boolean menor(String a, String b) {
		return compareIs(a, b, -1);
	}

	public static boolean maiorOuIgual(String a, String b) {
		return maior(a, b) || eq(a, b);
	}

	public static boolean menorOuIgual(String a, String b) {
		return menor(a, b) || eq(a, b);
	}

}
