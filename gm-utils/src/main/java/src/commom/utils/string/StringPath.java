package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class StringPath {

	public static Itens<String> CHARS = StringConstants.MINUSCULAS.concat(StringConstants.NUMEROS).add("/").add("-").add("_");

	public static String formatParcial(String s) {

		s = StringCases.exec(s, "/");
		s = StringExtraiCaracteres.exec(s, StringPath.CHARS);
		s = StringReplace.exec(s, "//", "/");

		String fim;

		do {
			fim = s;
			s = s.replace("/0", "/");
			s = s.replace("/1", "/");
			s = s.replace("/2", "/");
			s = s.replace("/3", "/");
			s = s.replace("/4", "/");
			s = s.replace("/5", "/");
			s = s.replace("/6", "/");
			s = s.replace("/7", "/");
			s = s.replace("/8", "/");
			s = s.replace("/9", "/");
		} while (!StringCompare.eq(s, fim));

		return s;
	}

}
