import StringCases from './StringCases';
import StringCompare from './StringCompare';
import StringConstants from './StringConstants';
import StringExtraiCaracteres from './StringExtraiCaracteres';
import StringReplace from './StringReplace';

export default class StringPath {

	static CHARS = StringConstants.MINUSCULAS.concat(StringConstants.NUMEROS).add("/").add("-").add("_");

	static formatParcial(s) {

		s = StringCases.exec(s, "/");
		s = StringExtraiCaracteres.exec(s, StringPath.CHARS);
		s = StringReplace.exec(s, "//", "/");

		let fim;

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
