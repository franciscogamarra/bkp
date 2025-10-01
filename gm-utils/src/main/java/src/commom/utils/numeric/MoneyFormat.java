package src.commom.utils.numeric;

import src.commom.utils.comum.SeparaMilhares;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringRight;

public class MoneyFormat {

	public static String exec(String s) {

		s = StringExtraiNumeros.exec(s);

		if (StringEmpty.is(s)) {
			return "0,00";
		}

		while (s.startsWith("0")) {
			s = s.substring(1);
		}

		if (StringEmpty.is(s)) {
			return "0,00";
		}

		int len = StringLength.get(s);

		if (len == 1) {
			return "0,0" + s;
		}

		if (len == 2) {
			return "0," + s;
		}

		String centavos = StringRight.get(s, 2);
		s = StringRight.ignore(s, 2);
		s = SeparaMilhares.exec(s);

		return s + "," + centavos;

	}

}
