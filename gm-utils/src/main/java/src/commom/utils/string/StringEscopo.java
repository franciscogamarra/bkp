package src.commom.utils.string;

import gm.utils.exception.UException;

public class StringEscopo {

	public static final String exec(String s, String abre, String fecha) {

		if (StringEmpty.is(s)) {
			return null;
		}

		if (StringEmpty.is(abre)) {
			throw UException.runtime("abre is empty");
		}

		if (StringEmpty.is(fecha)) {
			throw UException.runtime("fecha is empty");
		}

		if (abre.equals(fecha)) {
			throw UException.runtime("abre == fecha");
		}

		s = StringAfterFirst.get(s, abre);

		if (StringEmpty.is(s) || !StringContains.is(s, fecha)) {
			return null;
		}

		String result = "";

		int aberturas = 1;

		while (aberturas > 0) {

			int f = s.indexOf(fecha);

			if (f == -1) {
				return null;
			}

			int a = s.indexOf(abre);

			if (a == -1 || a > f) {
				result += s.substring(0, f) + fecha;
				s = s.substring(f+1);
				aberturas--;
			} else {
				result += s.substring(0, a) + abre;
				s = s.substring(a+1);
				aberturas++;
			}

		}

		return StringRight.ignore(result, 1);

	}	
	
}
