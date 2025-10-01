package gm.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

public class ExpressaoMatematica {

	public static String resolve(String s) {

		s = StringTrim.plus(s).replace(" ", "");

		while (StringContains.is(s, "(")) {
			String b = StringBeforeFirst.get(s, ")");
			String a = StringAfterLast.get(b, "(");
			s = s.replace("("+a+")", resolve(a));
		}

		String before;

		do {
			before = s;
			s = s.replace("*-", "*n");
			s = s.replace("*-", "*n");
			s = s.replace("+-", "-");
			s = s.replace("--", "+");
			s = s.replace("++", "+");
			s = s.replace("-+", "-");
		} while (!before.contentEquals(s));

		if (s.startsWith("-")) {
			s = "n" + s.substring(1);
		}

		ListString list = ListString.separaPalavras(s);

		list.juntarComASuperiorSeEquals(".");
		for (int i = 0; i < 10; i++) {
			list.juntarFimComComecos(".", ""+i, "");
		}

		while (list.contains("*") || list.contains("/")) {

			int index;

			if (list.contains("*")) {
				if (!list.contains("/") || (list.indexOf("*") < list.indexOf("/"))) {
					index = list.indexOf("*");
				} else {
					index = list.indexOf("/");
				}
			} else {
				index = list.indexOf("/");
			}

			index--;

			String sa = list.remove(index).replace("n", "-");
			String sinal = list.remove(index);
			String sb = list.remove(index).replace("n", "-");

			boolean fracional = sa.contains(".") || sb.contains(".");

			if (fracional) {

				BigDecimal a = UBigDecimal.toBigDecimal(sa);
				BigDecimal b = UBigDecimal.toBigDecimal(sb);
				BigDecimal r;
				if (sinal.contentEquals("*")) {
					r = a.multiply(b);
				} else {
					r = a.divide(b, 250, RoundingMode.HALF_UP);
				}

				list.add(index, r.toString());

			} else {

				int a = IntegerParse.toInt(sa);
				int b = IntegerParse.toInt(sb);
				int r;
				if (sinal.contentEquals("*")) {
					r = a * b;
				} else {
					r = a / b;
				}

				list.add(index, "" + r);

			}

		}

		while (list.size() > 1) {

			String sa = list.remove(0).replace("n", "-");
			String sinal = list.remove(0);
			String sb = list.remove(0).replace("n", "-");

			boolean fracional = sa.contains(".") || sb.contains(".");

			if (fracional) {

				BigDecimal a = UBigDecimal.toBigDecimal(sa);
				BigDecimal b = UBigDecimal.toBigDecimal(sb);
				BigDecimal r;
				if (sinal.contentEquals("+")) {
					r = a.add(b);
				} else {
					r = a.subtract(b);
				}

				list.add(0, r.toString());

			} else {

				int a = IntegerParse.toInt(sa);
				int b = IntegerParse.toInt(sb);
				int r;
				if (sinal.contentEquals("+")) {
					r = a + b;
				} else {
					r = a - b;
				}

				list.add(0, "" + r);

			}

		}

		s = list.get(0);

		if (StringContains.is(s, ".")) {
			while (s.endsWith("0")) {
				s = StringRight.ignore1(s);
			}
		}

		return s;
	}

	public static void main(String[] args) {
		console.log(resolve("1.0/2"));
		console.log(resolve("1./2"));
		console.log(resolve("1.00000000000000000000000000/2"));
		console.log(resolve("1/2"));
		console.log(resolve("5/5+(5*5)*5*(8./9)+(8*(8-(14+8)))"));
		console.log(resolve("-++--++++++++++--8*(8-(14+8))"));
		console.log(resolve("8.0/9.0"));
		console.log(resolve("x*7"));
	}

}
