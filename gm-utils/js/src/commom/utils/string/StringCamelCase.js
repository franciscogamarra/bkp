import IntegerIs from '../integer/IntegerIs';
import StringBox from './StringBox';
import StringCompare from './StringCompare';
import StringConstants from './StringConstants';
import StringEmpty from './StringEmpty';
import StringExtraiCaracteres from './StringExtraiCaracteres';
import StringRemoveAcentos from './StringRemoveAcentos';
import StringTrim from './StringTrim';

export default class StringCamelCase {

	static exec(s) {

		s = StringTrim.plus(s);

		if (StringEmpty.is(s)) {
			return "";
		}

		if (StringCompare.eqIgnoreCase(s, "uf")) {
			return "uf";
		}

		s = StringRemoveAcentos.exec(s);
		let xx = "";

		while (!StringEmpty.is(s)) {
			let n = s.substring(0,1);
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
		s = StringExtraiCaracteres.exec(s, StringCamelCase.VALIDOS);
		s = StringTrim.plus(s);

		if (StringEmpty.is(s)) {
			return "";
		}

		s = s.replace("_", " ");

		s = " " + StringTrim.plus(s) + " ";

		s = s.replace(" d d d ", " ddd ");
		s = s.replace(" d d i ", " ddi ");
		s = s.replace(" e mail ", " email ");

		let box = new StringBox(s.trim());

		StringConstants.MINUSCULAS.forEach(x => {
			box.replace(" " + x, x.toUpperCase());
		});

		box.replace(" ", "");

		return box.get();

	}

}
StringCamelCase.VALIDOS = StringConstants.MINUSCULAS.concat(StringConstants.NUMEROS).concat2(" ");
