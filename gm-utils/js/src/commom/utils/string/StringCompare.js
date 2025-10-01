import Equals from '../object/Equals';
import StringEmpty from './StringEmpty';
import StringLength from './StringLength';

export default class StringCompare {

	static eqq(a, b) {

		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return StringCompare.eq(a, b);
		}

	}

	static eq(a, b) {

		if (Equals.is(a, b)) {
			return true;
		}
		if (StringLength.get(a) !== StringLength.get(b)) {
			return false;
		} else if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		} else if (StringEmpty.is(b)) {
			return false;
		}

		/* garante que a comparacao seja por conteudo e não por referencia */
		a += "";
		b += "";

		return Equals.is(a, b);

	}

	static eqIgnoreCase(a, b) {

		if (StringEmpty.is(a)) {
			return StringEmpty.is(b);
		}
		if (StringEmpty.is(b)) {
			return false;
		} else {
			return StringCompare.eq(a.toLowerCase(), b.toLowerCase());
		}

	}

	static compare(a, b) {
		if (StringCompare.eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b) || a.toLowerCase().startsWith(b.toLowerCase())) {
			return 1;
		} else if (b.toLowerCase().startsWith(a.toLowerCase())) {
			return -1;
		} else {
			return a.localeCompare(b);
		}
	}

	static compareNumeric(a, b) {

		if (StringCompare.eq(a, b)) {
			return 0;
		}
		if (StringEmpty.is(a)) {
			return -1;
		} else if (StringEmpty.is(b)) {
			return 1;
		} else if (StringLength.get(a) < StringLength.get(b)) {
			return -1;
		} else if (StringLength.get(b) < StringLength.get(a)) {
			return 1;
		} else {
			return a.localeCompare(b);
		}

	}

}
