package br.sicoob.src.app.shared.utils;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.utils.number.Numeric2;
import js.Js;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;

@From("@shared/utils/utils")
public class Utils {
	public static boolean isNull(Object o) {
		return o == null || o == Js.undefined();
	}

	public static String formatNumber(Number v) {

		if (v == null) {
			return "";
		} else {
			Numeric2 n = new Numeric2(v.doubleValue());
			if (n.isZero()) {
				return "";
			} else {
				return n.toString();
			}
		}
		
	}
	
	public static String formatRs(Number v) {
		String s = formatNumber(v);
		if (StringEmpty.is(s)) {
			return "";
		} else {
			return "R$ " + s;
		}
	}

	public static String formatPer(Number v) {
		String s = formatNumber(v);
		if (StringEmpty.is(s)) {
			s = "0,00";
		}
		return s + " %";
	}
	
	public static Double toDouble(Object o) {
		if (Null.is(o)) {
			return 0.00;
		}
		Double value = Js.Number(o);
		if (Js.isNaN(value)) {
			return 0.0;
		} else {
			return value;
		}
	}
	
}
