package gm.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gm.utils.exception.UException;
import js.support.console;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

public class UBigDecimal {

	public static BigDecimal toMoney(Object o) {
		return toBigDecimal(o, 2);
	}

	public static BigDecimal toBigDecimal(Integer i) {
		return toBigDecimal((double) i);
	}

	public static BigDecimal toBigDecimal(String s) {
		return toBigDecimal(Double.valueOf(s));
	}

	public static BigDecimal toBigDecimal(Double x) {
		if (x == null) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(x);
	}

	public static void main(String[] args) {
		console.log(format(BigDecimal.ZERO, 4));
	}

	public static String format(BigDecimal o, int precision) {
		if (o == null) {
			return null;
		}
		o = toBigDecimal(o, precision);
		return o.toString();
	}

	@SuppressWarnings("rawtypes")
	public static BigDecimal toBigDecimal(Object o, int casas) {

		if (o == null) {
			return setScale(BigDecimal.ZERO, casas);
		}

		if (o instanceof Numeric) {
			Numeric n = (Numeric) o;
			return n.getValor();
		}

		String s = o.toString();

		if (StringEmpty.is(s)) {
			return BigDecimal.ZERO;
		}

		if (StringContains.is(s, ",")) {
			if (StringContains.is(s, ".")) {
				if (s.indexOf(".") < s.indexOf(",")) {
					s = s.replace(".", "");
					s = s.replace(",", ".");
				} else {
					s = s.replace(",", "");
				}
			} else {
				s = s.replace(",", ".");
			}
		}

		try {
			s = s.replace(",", "");
			BigDecimal bd = new BigDecimal(s);
			return bd.setScale(casas, UNumber.ROUNDING_MODE);
		} catch (Exception e) {
			throw UException.runtime("Não foi possível converter '" + s + "' para BigDecimal");
		}
	}

	public static boolean eq(BigDecimal a, BigDecimal b) {
		return a == null ? b == null : a.equals(b);
	}
	public static boolean ne(BigDecimal a, BigDecimal b) {
		return !eq(a,b);
	}
	
	public static BigDecimal setScale(BigDecimal o, int scale) {
		
		if (o == null) {
			return null;
		}
		
		return o.setScale(scale, RoundingMode.HALF_DOWN);
		
	}

	public static BigDecimal parse(Object o) {
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof BigDecimal) {
			return (BigDecimal) o;
		}

		if (o instanceof Object[]) {
			
			Object[] os = (Object[]) o;
			
			if (os.length == 0) {
				return null;
			}
			
			return parse(os[0]);
			
		}
		
		Double x = UDouble.toDouble(o);
		return x == null ? null : BigDecimal.valueOf(x);
		
	}
	
	public static BigDecimal setScale2(BigDecimal o) {
		return setScale(o, 2);
	}

	public static BigDecimal setScale4(Double o) {
		
		if (o == null) {
			return null;
		}
		
		return setScale4(BigDecimal.valueOf(o));
		
	}
	
	public static BigDecimal setScale4(BigDecimal o) {
		return setScale(o, 4);
	}

	public static BigDecimal setScale8(BigDecimal o) {
		return setScale(o, 8);
	}
}
