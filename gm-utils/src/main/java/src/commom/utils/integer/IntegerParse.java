package src.commom.utils.integer;

import java.math.BigDecimal;
import java.math.BigInteger;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.abstrato.ExtraidId;
import gm.utils.abstrato.ObjetoComId;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;

public class IntegerParse {
	
	public static int toIntDef(Object o, int def) {
		if (Null.is(o)) {
			return def;
		}
		if (IntegerIs.is(o)) {
			return toInt(o);
		}
		try {
			o = toInt(o);
			if (IntegerIs.is(o)) {
				return (int) o;
			}
			return def;
		} catch (Exception e) {
			return def;
		}

	}

	@PodeSerNull
	public static Integer toInt(Object o) {
		if (Null.is(o)) {
			return null;
		}
		return Js.parseInt(o);
	}

	public static int toIntSafe(Object o) {
		Integer i = toInt(o);
		if (Null.is(i)) {
			throw new Error("i == null");
		} else {
			return i;
		}
	}
	
	@IgnorarDaquiPraBaixo
	
	public static class Js {
		
		private static Integer toInt(Object o, Integer def) {
			
			if (o == null) {
				return def;
			}
			
			if (o instanceof Object[]) {
				Object[] os = (Object[]) o;
				if (os.length == 0) {
					return def;
				}
				o = os[0];
				return toInt(o, def);
			}

			if (o instanceof Integer) {
				return (Integer) o;
			}
			
			if (o instanceof Long) {
				Long x = (Long) o;
				return x.intValue();
			}
			
			if (o instanceof Double) {
				Double d = (Double) o;
				int i = d.intValue();
				d = d - i;
				if (d > 0.5) {
					i++;
				}
				return i;
			}
			
			if (o instanceof BigInteger) {
				BigInteger x = (BigInteger) o;
				return x.intValue();
			}
			
			if (o instanceof BigDecimal) {
				BigDecimal x = (BigDecimal) o;
				return x.intValue();
			}
			
			if (o instanceof Byte) {
				Byte b = (Byte) o;
				return b.intValue();
			}

			if (o instanceof java.lang.Number) {
				java.lang.Number b = (java.lang.Number) o;
				return b.intValue();
			}
			
			String s = o.toString().trim();

			if ( StringExtraiNumeros.exec(s).isEmpty() ) {
				return def;
			}

			try {
				return Integer.parseInt(s);
			} catch (Exception e) {}

			return def;

		}		

		public static Integer parseInt(Object o) {
			
			if (o == null) {
				return null;
			}
			if (IntegerIs.is(o)) {
				return Js.toInt(o, 0);
			}
			if ( o instanceof ObjetoComId ) {
				Object obj = ExtraidId.exec(o);
				
				if (obj instanceof Integer) {
					return (Integer) obj;
				} else if (obj instanceof Long) {
					Long x = (Long) obj;
					return x.intValue();
				} else {
					throw new NaoImplementadoException();
				}
				
			}
			if (o instanceof String && StringEmpty.is((String) o)) {
				return null;
			}
			if (o instanceof Double) {
				Double d = (Double) o;
				return d.intValue();
			}
			throw UException.runtime("Não é um inteiro: " + o);
			
		}
		
	}
	

}
