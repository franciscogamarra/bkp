import ArrayLst from '../array/ArrayLst';
import IntegerCompare from '../integer/IntegerCompare';
import IntegerParse from '../integer/IntegerParse';
import StringAfterFirst from '../string/StringAfterFirst';
import StringBeforeFirst from '../string/StringBeforeFirst';
import StringCompare from '../string/StringCompare';
import StringContains from '../string/StringContains';
import StringEmpty from '../string/StringEmpty';
import StringExtraiCaracteres from '../string/StringExtraiCaracteres';
import StringLength from '../string/StringLength';

export default class NumericCompare {

	static prepare(s) {

		s = StringExtraiCaracteres.exec(s, NumericCompare.CHARS);

		if (StringEmpty.is(s)) {
			return "0,0";
		}
		if (!StringContains.is(s, ",")) {
			s += ",0";
		} else if (s.endsWith(",")) {
			s += "0";
		}

		if (s.startsWith(",")) {
			s = "0" + s;
		}

		return s;

	}

	static compare(a, b) {

		a = NumericCompare.prepare(a);
		b = NumericCompare.prepare(b);

		if (StringCompare.eq(a, b)) {
			return 0;
		}

		let int_a = IntegerParse.toInt(StringBeforeFirst.get(a, ","));
		let int_b = IntegerParse.toInt(StringBeforeFirst.get(b, ","));

		if (int_a < int_b) {
			return -1;
		}
		if (int_a > int_b) {
			return 1;
		}

		a = StringAfterFirst.get(a, ",");
		b = StringAfterFirst.get(b, ",");

		let len_a = StringLength.get(a);
		let len_b = StringLength.get(b);

		if (len_a > len_b) {
			while (len_a > len_b) {
				b += "0";
				len_b++;
			}
		} else if (len_a < len_b) {
			while (len_a < len_b) {
				a += "0";
				len_a++;
			}
		}

		int_a = IntegerParse.toInt(a);
		int_b = IntegerParse.toInt(b);

		if (int_a < int_b) {
			return -1;
		}
		if (int_a > int_b) {
			return 1;
		} else {
			return 0;
		}

	}

	static compareIs(a, b, value) {
		let x = NumericCompare.compare(a, b);
		return IntegerCompare.eq(x, value);
	}

	static eq(a, b) {
		return NumericCompare.compareIs(a, b, 0);
	}

	static ne(a, b) {
		return !NumericCompare.eq(a,b);
	}

	static isZero(s) {
		return NumericCompare.eq(s, "0");
	}

	static maior(a, b) {
		return NumericCompare.compareIs(a, b, 1);
	}

	static menor(a, b) {
		return NumericCompare.compareIs(a, b, -1);
	}

	static maiorOuIgual(a, b) {
		return NumericCompare.maior(a, b) || NumericCompare.eq(a, b);
	}

	static menorOuIgual(a, b) {
		return NumericCompare.menor(a, b) || NumericCompare.eq(a, b);
	}

}
NumericCompare.CHARS = ArrayLst.build("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",");
