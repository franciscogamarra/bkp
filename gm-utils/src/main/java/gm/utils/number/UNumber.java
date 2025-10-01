package gm.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gm.utils.comum.UObject;
import gm.utils.exception.UException;
import gm.utils.map.MapSO;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringRight;

public class UNumber {

	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_DOWN;

	public static void trataSetInteger(Integer valor, Integer minimo, Integer maximo, String nome) {
		if (valor == null) {
			return;
		}
		if (minimo != null && valor < minimo) {
			throw UException.runtime("O valor mínimo para o campo " + nome + " é " + minimo + ", por isso o valor " + valor + " for rejeitado!");
		}
		if (maximo != null && valor > maximo) {
			throw UException.runtime("O valor máximo para o campo " + nome + " é " + maximo + ", por isso o valor " + valor + " for rejeitado!");
		}
	}

	public static BigDecimal roundBigDecimal(BigDecimal valor, int integers, int fraction) {

		if (valor == null) {
			return null;
		}

		Numeric<?> n;
		if (fraction == 1) {
			n = new Numeric1(valor);
		} else if (fraction == 2) {
			n = new Numeric2(valor);
		} else {
			throw UException.runtime("Não tratado: " + fraction);
		}
		int maxLen = integers + 1;
		String string = n.toString().replace(".", "");
		if (string.length() > maxLen) {
			throw UException.runtime("Ultrapassou: " + string);
		}
		return n.getValor();
	}

	public static String format00(String s, Integer casas) {
		while (s.length() < casas) {
			s = "0" + s;
		}
		return s;
	}
	@Deprecated//chamar direto
	public static String format00(Integer i, Integer casas) {
		return IntegerFormat.zerosEsquerda(i, casas);
	}
	public static String format00(Long i, Integer casas) {
		if (casas < 1) {
			throw UException.runtime("O parâmetro casas deve ser > 0!");
		}
		String s = "";
		if (!UObject.isEmpty(i)) {
			s = i.toString();
		}
		return format00(s, casas);
	}

	@Deprecated
	public static String format00(Integer i) {
		return IntegerFormat.xx(i);
	}

	public static boolean isNumber(Object a) {
		if (IntegerIs.is(a) || (a instanceof Double) || (a instanceof BigDecimal) || (a instanceof Numeric)) {
			return true;
		}
		return false;
	}

	public static MapSO getSimpleMap(BigDecimal o) {
		return getSimpleMap( new Numeric2(o) );
	}

	public static MapSO getSimpleMap(Numeric2 o) {
		if (o == null) {
			return null;
		}
		MapSO map = new MapSO();
		map.put("id", o.toDouble());
		map.put("text", o.toString());
		map.put("tipo", "Numeric2");
		return map;
	}

	public static BigDecimal toMoney(Double d) {
		BigDecimal o = new BigDecimal(d);
		return o.setScale(2, ROUNDING_MODE);
	}

	public static BigDecimal toBigDecimal(Double d, int decimais) {
		if (d == null) {
			d = 0.0;
		}
		BigDecimal o = new BigDecimal(d);
		return o.setScale(decimais, ROUNDING_MODE);
	}

	public static String formatMoney(Double d) {
		return formatMoney(toMoney(d));
	}

	public static String formatMoney(BigDecimal o) {
		String s = o.toString();
		s = separarMilhares(StringBeforeFirst.get(s, ".")) + "," + StringAfterFirst.get(s, ".");
		return "R$ " + s;
	}
	public static String separarMilhares(Integer i) {
		if (i == null) {
			return null;
		}
		return separarMilhares(i.toString());
	}
	public static String separarMilhares(String s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		s = StringExtraiNumeros.exec(s);
		if (StringLength.get(s) < 4) {
			return s;
		}
		String result = "";
		while (StringLength.get(s) > 3) {
			result = "." + StringRight.get(s, 3) + result;
			s = StringRight.ignore(s, 3);
		}
		return s + result;
	}

}
