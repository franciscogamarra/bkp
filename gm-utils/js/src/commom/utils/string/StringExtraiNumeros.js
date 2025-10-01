import ArrayLst from '../array/ArrayLst';
import StringEmpty from './StringEmpty';
import StringExtraiCaracteres from './StringExtraiCaracteres';

export default class StringExtraiNumeros {

	static exec(s) {
		return StringExtraiCaracteres.exec(s, StringExtraiNumeros.NUMEROS);
	}

	static nullIfEmpty(s) {
		s = StringExtraiNumeros.exec(s);
		if (StringEmpty.is(s)) {
			return null;
		}
		return s;
	}

}
StringExtraiNumeros.NUMEROS = ArrayLst.build("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
