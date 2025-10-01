package gm.utils.number;

import java.math.BigDecimal;
import java.math.BigInteger;

import gm.utils.exception.UException;
import gm.utils.string.UString;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRepete;
import src.commom.utils.string.StringRight;

public class UDouble {

	public static Double toDouble(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Double) {
			return (Double) o;
		}
		if (o instanceof Number) {
			Number b = (Number) o;
			return b.doubleValue();
		}
		if (o instanceof BigInteger) {
			BigInteger b = (BigInteger) o;
			return b.doubleValue();
		}
		if (o instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) o;
			return b.doubleValue();
		}
		if (o instanceof Integer) {
			Integer b = (Integer) o;
			return b.doubleValue();
		}
		if (o instanceof Long) {
			Long b = (Long) o;
			return b.doubleValue();
		}
		if (o instanceof String) {
			String s = (String) o;
			return toDouble(s);
		}
		if (o instanceof Numeric) {
			Numeric<?> b = (Numeric<?>) o;
			return b.toDouble();
		}
		
//		if (o instanceof MapSO) {
//			MapSO map = (MapSO) o;
//			if (map.containsKey("valor") && map.containsKey("casas")) {
//
//			}
//		}
		throw UException.runtime("Não sei tratar: " + o.getClass());
	}

	public static Double toDouble(String s) {
		return toDouble(s, 10);
	}

	public static Double toDouble(String s, int limiteDecimal) {
		if (StringEmpty.is(s)) {
			return null;
		}
		s = s.trim();
		if (s.endsWith(",00.0")) {
			s = s.replace(",00.0", "");
		}
		if (s.endsWith(",00")) {
			s = s.replace(",00", "");
		}
		if (StringEmpty.is(s)) {
			return null;
		}
		if (StringContains.is(s, ".")) {
			while (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			}
			if (s.endsWith(".")) {
				s += "0";
			}
		} else if (IntegerIs.is(s)) {
			return IntegerParse.toIntDef(s, 0) + 0.0;
		} else if (StringContains.is(s, ",") && UString.ocorrencias(s, ",") == 1) {
			s = s.replace(",", ".");
			return toDouble(s, limiteDecimal);
		} else {
			s += ".0";
		}

		while (StringAfterFirst.get(s, ".").length() > limiteDecimal) {
			s = StringRight.ignore1(s);
		}

		if (s.replace(".", "").replace("0", "").isEmpty()) {
			return 0d;
		}

		Double d = Double.parseDouble(s);
		String string = d.toString();
		if (!string.equals(s)) {

			//algumas excessoes que ocorrem no parseDouble
			if (s.contentEquals("0.000001")) {
				return 0.000001;
			} else if (s.contentEquals("0.00001")) {
				return 0.00001;
			} else if (s.contentEquals("0.0001")) {
				return 0.0001;
			}

			throw UException.runtime("deu dif!!! : " + d + " ; " + s);
		}
		return d;
	}

	public static String format(Double d, int casas) {

		if (d == 0) {
			return "";
		}

		String s = d.toString();
		String afterFirst = StringAfterFirst.get(s, ".");
		s = s.replace(".", ",");

		if (afterFirst.length() == casas) {
			return s;
		}

		if (afterFirst.length() < casas) {
			s += StringRepete.exec("0", casas - afterFirst.length());
			return s;
		}

		while (s.length() - s.indexOf(",") < casas + 1) {
			s += "0";
		}

		return s;

	}

	public static boolean isEmptyOrZero(Double value) {
		return value == null || value == 0;
	}

}
