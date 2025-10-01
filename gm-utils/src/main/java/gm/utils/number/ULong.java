package gm.utils.number;

import java.math.BigInteger;

import gm.utils.abstrato.GetIdLong;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;

public class ULong {

	public static Long toLong(Object o) {
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof Long) {
			return (Long) o;
		}

		if (o instanceof Integer) {
			Integer i = (Integer) o;
			return i.longValue();
		}

		if (o instanceof BigInteger) {
			BigInteger i = (BigInteger) o;
			return i.longValue();
		}

		if (o instanceof Number) {
			Number i = (Number) o;
			return i.longValue();
		}
		
		if (o instanceof GetIdLong) {
			GetIdLong i = (GetIdLong) o;
			return i.getId();
		}
		
		Class<?> classe = o.getClass();

		if (classe.equals(long.class)) {
			return (long) o;
		}
		
		if (classe.equals(int.class)) {
			Integer i = (int) o;
			return i.longValue();
		}

		String s = StringExtraiNumeros.exec(o.toString());
		if (StringEmpty.is(s)) {
			return null;
		}
		return Long.parseLong(s);
	}

	public static boolean isLong(Object o) {
		if (o == null) {
			return false;
		}
		if ((o instanceof Long) || (o instanceof Integer) || (o instanceof BigInteger)) {
			return true;
		}
		if (o instanceof String) {
			try {
				Long.parseLong(o.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

}
