import SeparaMilhares from '../comum/SeparaMilhares';
import StringEmpty from '../string/StringEmpty';
import StringExtraiNumeros from '../string/StringExtraiNumeros';
import StringLength from '../string/StringLength';
import StringRight from '../string/StringRight';

export default class MoneyFormat {

	static exec(s) {

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

		let len = StringLength.get(s);

		if (len === 1) {
			return "0,0" + s;
		}

		if (len === 2) {
			return "0," + s;
		}

		let centavos = StringRight.get(s, 2);
		s = StringRight.ignore(s, 2);
		s = SeparaMilhares.exec(s);

		return s + "," + centavos;

	}

}
