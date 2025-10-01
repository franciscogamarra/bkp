import StringCompare from './StringCompare';
import StringConstants from './StringConstants';
import StringEmpty from './StringEmpty';
import StringExtraiCaracteres from './StringExtraiCaracteres';
import StringPrimeiraMaiuscula from './StringPrimeiraMaiuscula';
import StringPrimeiraMinuscula from './StringPrimeiraMinuscula';

export default class StringJava {

	static exec(s, primeiraMaiuscula) {

		if (StringEmpty.is(s)) {
			return null;
		}

		s = StringExtraiCaracteres.exec(s, StringConstants.JAVA_LETTERS);

		if (StringEmpty.is(s)) {
			return null;
		}

		if (primeiraMaiuscula) {
			s = StringPrimeiraMaiuscula.exec(s);
		} else {
			s = StringPrimeiraMinuscula.exec(s);
		}

		return s;

	}

	static isValid(s, primeiraMaiuscula) {

		if (StringEmpty.is(s)) {
			return false;
		}

		let x = StringJava.exec(s, primeiraMaiuscula);

		return StringCompare.eq(s, x);

	}

}
