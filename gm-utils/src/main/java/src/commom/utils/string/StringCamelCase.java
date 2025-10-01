package src.commom.utils.string;

import gm.utils.comum.SystemPrint;
import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerIs;

public class StringCamelCase {

	private static final Itens<String> VALIDOS = StringConstants.MINUSCULAS.concat(StringConstants.NUMEROS).copy().add(" ");

	public static String exec(String s) {

		s = StringTrim.plus(s);

		if (StringEmpty.is(s)) {
			return "";
		}

		if (StringCompare.eqIgnoreCase(s, "uf")) {
			return "uf";
		}

		s = StringRemoveAcentos.exec(s);
		String xx = "";

		while (!StringEmpty.is(s)) {
			String n = s.substring(0,1);
			s = s.substring(1);
			if (StringCompare.eq(n, n.toUpperCase())) {
				xx += " ";
			}
			xx += n;
		}

		s = xx;

		while (IntegerIs.is(s.substring(0, 1))) {
			s = s.substring(1);
			if (s.isEmpty()) {
				return null;
			}
		}

		s = s.toLowerCase();
		s = StringExtraiCaracteres.exec(s, VALIDOS);
		s = StringTrim.plus(s);

		if (StringEmpty.is(s)) {
			return "";
		}

		s = s.replace("_", " ");

		s = " " + StringTrim.plus(s) + " ";

		s = s.replace(" d d d ", " ddd ");
		s = s.replace(" d d i ", " ddi ");
		s = s.replace(" e mail ", " email ");

		StringBox box = new StringBox(s.trim());

		StringConstants.MINUSCULAS.forEach(x -> {
			box.replace(" " + x, x.toUpperCase());
		});

		box.replace(" ", "");

		return box.get();

	}
	
	
	public static void main(String[] args) {
		SystemPrint.ln(exec("Lotérica fixa"));
	}

}
