package src.commom.utils.string;

import gm.utils.comum.SystemPrint;

public class StringToKebabCase {

	private StringToKebabCase() {}

	public static String exec(String s) {
		
		s = s.trim();
		
		String last = s.substring(0, 1);
		String ss = last.toLowerCase();
		s = s.substring(1);
		
		while (!StringEmpty.is(s)) {
			
			String c = StringRemoveAcentos.exec(s.substring(0, 1));
			
			s = s.substring(1);
			
			if (c.trim().isEmpty()) {
				ss += "-";
				last = "-";
				continue;
			}
			
			if (StringConstants.NUMEROS.contains(c)) {
				ss += c;
				last = c;
				continue;
			}
			
			if (StringConstants.MAIUSCULAS.contains(c)) {
				if (StringConstants.MAIUSCULAS.contains(last)) {
					ss += c;
					last = c;
				} else {
					if (!StringCompare.eq(last, "-")) {
						ss += "-";
					}
					last = c;
					ss += c.toLowerCase();
				}
				continue;
			}
			
			if (StringConstants.MINUSCULAS.contains(c)) {
				ss += c;
				last = c;
				continue;
			}

			if (!StringCompare.eq(last, "-")) {
				ss += "-";
				last = "-";
			}
			
		}

		while (ss.startsWith("-")) {
			ss = ss.substring(1);
		}
		
		while (ss.endsWith("-")) {
			ss = StringRight.ignore1(ss);
		}
		
		ss = StringReplace.exec(ss, "----", "-");
		ss = StringReplace.exec(ss, "---", "-");
		ss = StringReplace.exec(ss, "--", "-");

		return ss.trim();

	}

	public static void main(String[] args) {
		SystemPrint.ln(exec("Quantidade Máxima de Apostas no Carrinho de Apostas."));
	}

}
