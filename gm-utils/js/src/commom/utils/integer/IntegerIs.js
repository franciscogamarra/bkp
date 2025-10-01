import Null from '../object/Null';
import StringCompare from '../string/StringCompare';
import StringEmpty from '../string/StringEmpty';
import StringExtraiNumeros from '../string/StringExtraiNumeros';
import StringLength from '../string/StringLength';
import StringParse from '../string/StringParse';
import StringRight from '../string/StringRight';
import StringTrim from '../string/StringTrim';

export default class IntegerIs {

	static is(o) {

		if (Null.is(o)) {
			return false;
		}

		if (Number.isInteger(o)) {
			return true;
		}

		let s = StringParse.get(o);
		s = StringTrim.plus(s);

		if (StringEmpty.is(s)) {
			return false;
		}

		if (s.endsWith(".")) {
			s = StringRight.ignore1(s);
		}

		if (s.startsWith("-")) {
			s = s.substring(1);
		}

		let n = StringExtraiNumeros.exec(s);

		if (!StringCompare.eq(s, n)) {
			return false;
		}

		let len = StringLength.get(s);

		if (len > 10) {
			return false;
		}
		if (len < 10) {
			return true;
		} else {
			return StringCompare.compare(s, "2147483647") < 1;
		}

	}

}
