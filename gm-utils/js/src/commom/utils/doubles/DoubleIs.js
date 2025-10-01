import IntegerIs from '../integer/IntegerIs';
import Null from '../object/Null';
import StringCompare from '../string/StringCompare';
import StringContains from '../string/StringContains';
import StringEmpty from '../string/StringEmpty';
import StringExtraiCaracteres from '../string/StringExtraiCaracteres';
import StringExtraiNumeros from '../string/StringExtraiNumeros';
import StringParse from '../string/StringParse';
import StringSplit from '../string/StringSplit';

export default class DoubleIs {

	static is(o) {

		if (IntegerIs.is(o)) {
			return true;
		}

		if (Null.is(o)) {
			return false;
		}

		let s = StringParse.get(o);

		if (StringEmpty.is(s)) {
			return false;
		}

		let n = StringExtraiCaracteres.exec(s, DoubleIs.NUMEROS);

		if (!StringCompare.eq(s, n)) {
			return false;
		}

		let ints;
		let decs;

		if (StringContains.is(s, ".")) {

			if (s.startsWith(".")) {
				s = "0" + s;
			}

			let list = StringSplit.exec(s, ".");

			if (list.size() !== 2) {
				return false;
			}

			ints = list.get(0);
			decs = list.get(1);

		} else {
			ints = s;
			decs = "0";
		}

		return IntegerIs.is(ints) && IntegerIs.is(decs);

	}

}
DoubleIs.NUMEROS = StringExtraiNumeros.NUMEROS.concat2(".");
