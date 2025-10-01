import StringCamelCase from './StringCamelCase';
import StringConstants from './StringConstants';
import StringEmpty from './StringEmpty';
import StringRemoveAcentos from './StringRemoveAcentos';
import StringReplace from './StringReplace';
import StringTrim from './StringTrim';

export default class StringCases {

	static kebab(s) {
		return StringCases.exec(s, "-");
	}

	static snake(s) {
		return StringCases.exec(s, "_");
	}

	static dot(s) {
		return StringCases.exec(s, ".");
	}

	static camel(s) {
		return StringCamelCase.exec(s);
	}

	static exec(s, separator) {

		if (StringEmpty.is(s)) {
			return "";
		}

		let original = s;

		let fim = s.endsWith(" ");

		s = StringRemoveAcentos.exec(s);

		for (let i = 0; i < StringConstants.MAIUSCULAS.size(); i++) {
			let x = StringConstants.MAIUSCULAS.get(i);
			s = StringReplace.exec(s, x, " " + x);
		}

		s = StringTrim.plus(s);
		s = s.toLowerCase();

		if (fim) {
			s += " ";
		}

		s = StringReplace.exec(s, " ", separator);
		s = StringReplace.exec(s, separator+separator, separator);

		if (StringEmpty.is(s)) {
			console.log(original);
		}

		return s;

	}

	static upper(s) {
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.toUpperCase();
	}

	static lower(s) {
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.toLowerCase();
	}

}
